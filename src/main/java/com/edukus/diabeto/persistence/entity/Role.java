package com.edukus.diabeto.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Role {

  @Id
  @GeneratedValue
  @JsonIgnore
  private Long id;
  @NotNull
  private String code;
  @NotNull
  private String value;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
  private AppSetting appSetting;

}
