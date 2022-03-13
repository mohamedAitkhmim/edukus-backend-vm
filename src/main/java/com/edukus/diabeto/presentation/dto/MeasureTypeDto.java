package com.edukus.diabeto.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class MeasureTypeDto {

  private String code;
  private String label;
  private String unit;
  private Float min;
  private Float max;
  private Float minSystolic;
  private Float maxSystolic;
  private Float minDiastolic;
  private Float maxDiastolic;
  private Float minRefPrePrandial;
  private Float maxRefPrePrandial;
  private Float minRefPostPrandial;
  private Float maxRefPostPrandial;


}
