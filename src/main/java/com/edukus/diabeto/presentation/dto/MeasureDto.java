package com.edukus.diabeto.presentation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class MeasureDto {

  private Long id;
  private Double value;
  private LocalDateTime dateTime;
  private String prandial;
  private String reason;
  private Double height;
  private Double weight;
  private Double diastolic;
  private Double systolic;

  public Double getBodyMass(){
    if(height == null || weight == null){
      return null;
    }
    return weight/(height*height);
  }

  public String getTension(){
    if(systolic == null || diastolic == null){
      return null;
    }
    return new DecimalFormat("###.#").format(systolic) + "/" + new DecimalFormat("###.#").format(diastolic);
  }

  @JsonIgnore
  public LocalDate getDate(){
    return dateTime.toLocalDate();
  }

}
