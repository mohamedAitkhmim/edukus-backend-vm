package com.edukus.diabeto.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Association {

  @Id
  @GeneratedValue
  @JsonIgnore
  private Long id;

  private String status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name ="patient")
  private User patient;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name ="doctor")
  private User doctor;

}
