package com.edukus.diabeto.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class  UserProfile {

  @Id
  @GeneratedValue
  @JsonIgnore
  private Long id;
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
  @NotNull
  private String email;
  @JsonIgnore
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name ="email", insertable=false, updatable=false)
  private User user;

}
