package com.edukus.diabeto.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "MeasureType")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "measure_type")
public class MeasureTypeEntity {

  @Id
  @GeneratedValue
  private Long id;
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
