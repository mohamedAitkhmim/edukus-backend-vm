package com.edukus.diabeto.service;

import com.edukus.diabeto.persistence.entity.Registration;
import com.edukus.diabeto.persistence.entity.Role;
import com.edukus.diabeto.persistence.entity.User;
import com.edukus.diabeto.persistence.repository.RegistrationRepository;
import com.edukus.diabeto.persistence.repository.RoleRepository;
import com.edukus.diabeto.persistence.repository.UserRepository;
import com.edukus.diabeto.presentation.dto.BaseSignUpDto;
import com.edukus.diabeto.presentation.dto.UserDto;
import com.edukus.diabeto.security.keycloak.UserKeycloak;
import com.edukus.diabeto.security.keycloak.dto.TokenDto;
import com.edukus.diabeto.utile.EmailSender;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegistrationServiceImpl {

  public static final String ADMIN_ROLE = "R03";
  @Value("${edukus.mail.subject}")
  private String emailSubject;
  @Value("${edukus.mail.content}")
  private String emailContent;

  RoleRepository roleRepository;
  UserRepository userRepository;
  RegistrationRepository registrationRepository;
  UserKeycloak userKeycloak;

  @Autowired
  EmailSender emailSender;

  Logger LOGGER = Logger.getLogger(RegistrationServiceImpl.class.getName());


  public RegistrationServiceImpl(RoleRepository roleRepository, UserRepository userRepository, RegistrationRepository registrationRepository,
      UserKeycloak userKeycloak)
  {
    this.roleRepository = roleRepository;
    this.userRepository = userRepository;
    this.registrationRepository = registrationRepository;
    this.userKeycloak = userKeycloak;
  }

  public UserDto register(BaseSignUpDto baseSignUpDto, boolean isAdmin) {

    Role role = roleRepository.findByCode(baseSignUpDto.getRoleId()).orElseThrow(() -> new RuntimeException("Role unavailable"));
    String email = baseSignUpDto.getEmail();

    Registration registration = registrationRepository.findByUser_Email(email).orElseGet(() -> {

      if(ADMIN_ROLE.equals(role.getCode()) && !isAdmin){
        throw new RuntimeException("forbidden to create admin account");
      }

      User newUser = new User();
      newUser.setEmail(email);
      newUser.setUserId(generateUserId());
      newUser.setRole(role);

      Registration newRegistration = new Registration();
      newRegistration.setUser(newUser);
      return newRegistration;
    });

    if(!role.getCode().equals(registration.getUser().getRole().getCode())){
      throw new RuntimeException("forbidden to change role");
    }

    String registrationChallenge = generateRandomNumberString();
    LocalDateTime registrationChallengeDate = getTimeNow();
    registration.setRegistrationChallenge(registrationChallenge);
    registration.setRegistrationChallengeDate(registrationChallengeDate);

    try{
      emailSender.sendEmail(email, emailSubject, emailContent+ " " + registrationChallenge);
    }
    catch (Exception e){
      LOGGER.warning("email not valid");
      LOGGER.warning(e.getMessage());
      throw new RuntimeException("email not valid");
    }
    return new UserDto(registrationRepository.save(registration).getUser().getUserId());
  }

  public TokenDto verifyEmail(String userId, String challenge) {

    Registration registration = registrationRepository.findByUser_UserId(userId).orElseThrow(() -> new RuntimeException("user unavailable"));

    if (getTimeNow().minusMinutes(5).isAfter(registration.getRegistrationChallengeDate())) {
      throw new RuntimeException("code expired");
    }

    if (!registration.getRegistrationChallenge().equals(challenge)) {
      throw new RuntimeException("code not valid");
    }

    String email = registration.getUser().getEmail();
    TokenDto tokenDto = userKeycloak.logWithKeycloak(email, challenge);
    String refreshTokenHash = tokenDto.getRefreshToken().split("\\.")[2];
    registration.setTokenHash(refreshTokenHash);
    registrationRepository.save(registration);
    return tokenDto;
  }

  private static String generateRandomNumberString() {
    return String.format("%06d", new Random().nextInt(999999));
  }

  private static LocalDateTime getTimeNow() {
    return LocalDateTime.now(ZoneId.of("UTC"));
  }


  private static String generateUserId() {
    return UUID.randomUUID().toString();
  }


}
