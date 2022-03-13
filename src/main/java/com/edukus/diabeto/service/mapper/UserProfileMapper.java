package com.edukus.diabeto.service.mapper;

import com.edukus.diabeto.persistence.entity.UserProfile;
import com.edukus.diabeto.presentation.dto.UserProfileDto;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;

public class UserProfileMapper {

  public static List<UserProfileDto> fromEntities(List<UserProfile> userProfiles) {
    return CollectionUtils.emptyIfNull(userProfiles).stream().map(UserProfileMapper::fromEntity).collect(Collectors.toList());
  }

  public static UserProfileDto fromEntity(UserProfile entity) {
    if (entity == null) {
      return UserProfileDto.builder().build();
    }
    return UserProfileDto.builder()
        .userId(entity.getUser().getUserId())
        .email(entity.getEmail())
        .fullName(entity.getFullName())
        .phoneNumber(entity.getPhoneNumber())
        .length(entity.getLength())
        .weight(entity.getWeight())
        .gender(entity.getGender())
        .diabetesType(entity.getDiabetesType())
        .birthDate(entity.getBirthDate())
        .infectionDate(entity.getInfectionDate())
        .description(entity.getDescription())
        .address(entity.getAddress())
        .spokenLanguages(entity.getSpokenLanguages())
        .experience(entity.getExperience())
        .education(entity.getEducation())
        .build();
  }

  public static UserProfileDto mapToDoctor(UserProfile entity, byte[] image) {
    if (entity == null) {
      return UserProfileDto.builder().build();
    }
    return UserProfileDto.builder()
        .image(image)
        .userId(entity.getUser().getUserId())
        .fullName(entity.getFullName())
        .gender(entity.getGender())
        .description(entity.getDescription())
        .address(entity.getAddress())
        .spokenLanguages(entity.getSpokenLanguages())
        .experience(entity.getExperience())
        .education(entity.getEducation())
        .build();
  }

  public static UserProfile toEntity(UserProfileDto dto) {
    if (dto == null) {
      return null;
    }
    return new UserProfile(null, dto.getFullName(), dto.getPhoneNumber(), dto.getLength(), dto.getWeight(), dto.getGender(), dto.getDiabetesType(), dto.getBirthDate(), dto.getInfectionDate(), dto.getDescription(), dto.getAddress(), dto.getSpokenLanguages(), dto.getExperience(),
        dto.getEducation(), dto.getEmail(), null);
  }

}
