package com.edukus.diabeto.service;

import com.edukus.diabeto.persistence.entity.UserProfile;
import com.edukus.diabeto.presentation.dto.UserDto;
import com.edukus.diabeto.presentation.dto.UserProfileDto;
import java.util.List;

public interface AssociationService {
  List<UserDto> getPatientsByDoctorId(String id);

  List<UserProfile> getDoctorsByPatientId(String id);

  UserProfileDto getDoctorProfileResume(String id);
}
