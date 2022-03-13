package com.edukus.diabeto.persistence.entity;


import java.time.LocalDateTime;
import java.util.List;
import javax.management.Notification;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "AppointmentEntity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "appointment")
public class AppointmentEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String service;
    private String location;
    private LocalDateTime date;
    private String note;
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appointment")
    private List<NotificationEntity> notifications;

}
