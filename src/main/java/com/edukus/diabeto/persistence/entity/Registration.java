package com.edukus.diabeto.persistence.entity;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
public class Registration {

  @Id
  @GeneratedValue
  private Long id;
  private String tokenHash;
  private String registrationChallenge;
  private LocalDateTime registrationChallengeDate;

  @OneToOne(cascade = CascadeType.ALL)
  @NotNull
  @JoinColumn(name ="email")
  private User user;

}
