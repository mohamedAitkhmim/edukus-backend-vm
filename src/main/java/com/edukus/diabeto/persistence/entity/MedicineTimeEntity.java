package com.edukus.diabeto.persistence.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "MedicineTimeEntity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medicine_time")
@IdClass(MedicineTimeEntityId.class)
public class MedicineTimeEntity {
    @Id
    private Long id;
    @Id
    private LocalDateTime time;

    private boolean notification;

}
