package com.edukus.diabeto.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "MeasureSubType")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "measure_sub_type")
public class MeasureSubTypeEntity {

  @Id
  @GeneratedValue
  private Long id;
  private String code;
  private String label;
  private String unit;
  @Column(name= "measure_type_id")
  private Long measureTypeId;

}
