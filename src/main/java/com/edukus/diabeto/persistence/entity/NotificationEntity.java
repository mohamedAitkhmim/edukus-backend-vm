package com.edukus.diabeto.persistence.entity;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "NotificationEntity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification")
public class NotificationEntity {

    @Id
    @GeneratedValue
    private Long id;
    private Integer time;

    @ManyToOne(fetch = FetchType.LAZY)
    private AppointmentEntity appointment;

}
