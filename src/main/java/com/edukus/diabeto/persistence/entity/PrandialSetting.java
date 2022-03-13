package com.edukus.diabeto.persistence.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "PrandialSetting")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "st_prandial")
public class PrandialSetting {

  @Id
  @GeneratedValue
  private Long id;
  private String code;
  private String label;
}
