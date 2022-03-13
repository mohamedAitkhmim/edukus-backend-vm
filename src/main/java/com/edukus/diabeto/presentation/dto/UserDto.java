package com.edukus.diabeto.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class UserDto implements Serializable {

  private String userId;
  private String fullName;
  private String length;
  private String weight;
  private String gender;
  private String diabetesType;
  private LocalDateTime birthDate;
  private LocalDateTime infectionDate;

  public UserDto(String userId) {
    this.userId = userId;
  }
}
