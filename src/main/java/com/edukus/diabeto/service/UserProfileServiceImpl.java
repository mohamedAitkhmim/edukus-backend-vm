package com.edukus.diabeto.service;

import com.edukus.diabeto.persistence.entity.User;
import com.edukus.diabeto.persistence.entity.UserFile;
import com.edukus.diabeto.persistence.entity.UserProfile;
import com.edukus.diabeto.persistence.repository.UserFileRepository;
import com.edukus.diabeto.persistence.repository.UserProfileRepository;
import com.edukus.diabeto.presentation.dto.UserProfileDto;
import com.edukus.diabeto.service.exception.ProfileException;
import com.edukus.diabeto.service.mapper.UserProfileMapper;
import com.edukus.diabeto.utile.RandomString;
import com.edukus.diabeto.utile.RoleType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {

  public static final String JPEG = ".jpeg";
  public static final String PATH_SEPARATOR = "/";
  private UserProfileRepository userProfileRepository;
  private UserFileRepository userFileRepository;

  @PersistenceContext
  private EntityManager em;

  @Value("${edukus.image.outputfile}")
  private String imageDirPath;

  public UserProfileServiceImpl(UserProfileRepository userProfileRepository, UserFileRepository userFileRepository) {
    this.userProfileRepository = userProfileRepository;
    this.userFileRepository = userFileRepository;
  }

  @Override
  public UserProfileDto getUserProfile(String id) {
    return UserProfileMapper.fromEntity(this.userProfileRepository.findByUser_Email(id).orElseThrow(() -> ProfileException.create(ProfileException.PROFILE_NOT_FOUND)));
  }

  @Override
  public void saveUserProfile(String email, UserProfileDto userProfile) {
    userProfileRepository.findByUser_Email(email).ifPresentOrElse(selectedUserProfile -> {
      selectedUserProfile.setFullName(userProfile.getFullName());
      selectedUserProfile.setPhoneNumber(userProfile.getPhoneNumber());
      selectedUserProfile.setBirthDate(userProfile.getBirthDate());
      selectedUserProfile.setLength(userProfile.getLength());
      selectedUserProfile.setWeight(userProfile.getWeight());
      selectedUserProfile.setGender(userProfile.getGender());
      selectedUserProfile.setDiabetesType(userProfile.getDiabetesType());
      selectedUserProfile.setInfectionDate(userProfile.getInfectionDate());
      userProfileRepository.save(selectedUserProfile);
    }, () -> {
      User user = em.getReference(User.class, email);
      if (user == null) {
        throw new RuntimeException("user not found");
      }
      userProfile.setEmail(email);
      userProfileRepository.save(UserProfileMapper.toEntity(userProfile));
    });

  }

  @Override
  public void saveImage(String username, byte[] image) throws IOException {

    String directoryName = imageDirPath.concat(username);
    String imageName = RandomString.generate(10) + JPEG;
    Files.createDirectories(Paths.get(directoryName));
    Path imagePath = Paths.get(directoryName + PATH_SEPARATOR + imageName);
    Files.write(imagePath, image);

    UserFile lastFile = userFileRepository.findByUser_EmailAndActiveTrue(username);
    if (lastFile != null) {
      lastFile.setActive(false);
      userFileRepository.save(lastFile);
    }

    User user = em.getReference(User.class, username);

    UserFile newFile = new UserFile();
    newFile.setName(imageName);
    newFile.setActive(true);
    newFile.setUser(user);
    userFileRepository.save(newFile);

  }

  @Override
  public byte[] getImage(String username) throws IOException {

    UserFile file = userFileRepository.findByUser_EmailAndActiveTrue(username);

    if (file != null) {
      String directoryName = imageDirPath.concat(username);
      return Files.readAllBytes(Paths.get(directoryName + PATH_SEPARATOR + file.getName()));
    }

    return new byte[0];
  }


  @Override
  public UserProfileDto getDoctorProfileResume(String id) throws IOException {
    UserProfile doctor = this.userProfileRepository.findDoctorByd(id, RoleType.DOCTOR.getValue());
    return UserProfileMapper.mapToDoctor(doctor, getImage(doctor.getEmail()));
  }
}
