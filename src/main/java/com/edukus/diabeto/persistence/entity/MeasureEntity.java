package com.edukus.diabeto.persistence.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Measure")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Measure")
public class MeasureEntity {

  @Id
  @GeneratedValue
  private Long id;
  @NotNull
  private Double value;
  @NotNull
  private LocalDateTime dateTime;
  private String prandial;
  private String reason;

  @Column(name= "email")
  private String email;
  @Column(name= "measure_type_id")
  private Long measureTypeId;
  @Column(name= "measure_sub_type_id")
  private Long measureSubTypeId;


  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name ="email", insertable = false, updatable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name ="measure_type_id", referencedColumnName = "id" , insertable = false, updatable = false)
  private MeasureTypeEntity measureTypeEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name ="measure_sub_type_id", referencedColumnName = "id" , insertable = false, updatable = false)
  private MeasureSubTypeEntity measureSubTypeEntity;

}
