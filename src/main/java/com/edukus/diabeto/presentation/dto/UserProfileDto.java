package com.edukus.diabeto.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class UserProfileDto {

  private byte[] image;
  private String userId;
  private String email;
  private String fullName;
  private String phoneNumber;
  private String length;
  private String weight;
  private String gender;
  private String diabetesType;
  private LocalDateTime birthDate;
  private LocalDateTime infectionDate;
  private String description;
  private String address;
  private String spokenLanguages;
  private String experience;
  private String education;

}
