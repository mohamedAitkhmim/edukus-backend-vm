package com.edukus.diabeto.presentation;


import com.edukus.diabeto.persistence.entity.UserProfile;
import com.edukus.diabeto.presentation.dto.UserProfileDto;
import com.edukus.diabeto.security.TokenParserUtil;
import com.edukus.diabeto.service.UserProfileServiceImpl;
import com.edukus.diabeto.service.exception.ProfileException;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ProfileController {

  private UserProfileServiceImpl userProfileServiceImpl;

  Logger LOGGER = Logger.getLogger(ProfileController.class.getName());

  public ProfileController(UserProfileServiceImpl userProfileServiceImpl) {
    this.userProfileServiceImpl = userProfileServiceImpl;
  }

  @GetMapping("/private/profile")
  public ResponseEntity<UserProfileDto> getProfile(Principal userPrincipal) {
    String preferredUsername = TokenParserUtil.extractToken(userPrincipal).getPreferredUsername();
    try {
      return ResponseEntity.status(HttpStatus.OK).body(userProfileServiceImpl.getUserProfile(preferredUsername));
    }
    catch (ProfileException exception){
      LOGGER.warning(exception.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

  }

  @PostMapping("/private/profile")
  public ResponseEntity<Void> saveProfile(@RequestBody UserProfileDto userProfileDto, Principal userPrincipal) {
    String preferredUsername = TokenParserUtil.extractToken(userPrincipal).getPreferredUsername();
    userProfileServiceImpl.saveUserProfile(preferredUsername, userProfileDto);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  }

  @PostMapping("/private/profile/image")
  public ResponseEntity<Void> saveProfileImage(@RequestPart("image") MultipartFile image, Principal userPrincipal) throws IOException {

    if (image.isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    String preferredUsername = TokenParserUtil.extractToken(userPrincipal).getPreferredUsername();
    userProfileServiceImpl.saveImage(preferredUsername, image.getBytes());
    return ResponseEntity.status(HttpStatus.OK).body(null);
  }

  @GetMapping("/private/profile/image")
  public ResponseEntity<byte[]> getProfileImage(Principal userPrincipal) throws IOException {

    String preferredUsername = TokenParserUtil.extractToken(userPrincipal).getPreferredUsername();

    byte[] imageBytes = userProfileServiceImpl.getImage(preferredUsername);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_JPEG);

    return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
  }

  @GetMapping("/public/doctors/{id}")
  public ResponseEntity<UserProfileDto> getDoctorProfile(@PathVariable String id) throws IOException {
    return ResponseEntity.status(HttpStatus.OK).body(userProfileServiceImpl.getDoctorProfileResume(id));
  }

}
