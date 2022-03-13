package com.edukus.diabeto.service;

import com.edukus.diabeto.persistence.entity.UserProfile;
import com.edukus.diabeto.presentation.dto.UserProfileDto;
import java.io.IOException;
import java.util.List;

public interface UserProfileService {

  UserProfileDto getUserProfile(String id);

  void saveUserProfile(String email, UserProfileDto userProfile);

  void saveImage(String username, byte[] image) throws IOException;

  byte[] getImage(String username) throws IOException;

  UserProfileDto getDoctorProfileResume(String id) throws IOException;
}
