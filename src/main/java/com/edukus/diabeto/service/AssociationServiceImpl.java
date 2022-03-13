package com.edukus.diabeto.service;

import com.edukus.diabeto.persistence.entity.UserProfile;
import com.edukus.diabeto.persistence.repository.AssociationRepository;
import com.edukus.diabeto.presentation.dto.UserDto;
import com.edukus.diabeto.presentation.dto.UserProfileDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AssociationServiceImpl implements AssociationService{

  AssociationRepository associationRepository;

  public AssociationServiceImpl(AssociationRepository associationRepository) {
    this.associationRepository = associationRepository;
  }

  @Override
  public List<UserDto> getPatientsByDoctorId(String id) {
    return associationRepository.findPatientsByDoctorId(id);
  }

  @Override
  public List<UserProfile> getDoctorsByPatientId(String id) {
    return associationRepository.findDoctorsByPatientId(id);
  }

  @Override
  public UserProfileDto getDoctorProfileResume(String id) {
    return null;
  }
}
