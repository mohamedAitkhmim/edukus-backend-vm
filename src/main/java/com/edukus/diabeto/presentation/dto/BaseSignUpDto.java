package com.edukus.diabeto.presentation.dto;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseSignUpDto implements Serializable {

  @NotBlank
  @NotEmpty
  private String email;
  @NotBlank
  @NotEmpty
  private String roleId;
}
