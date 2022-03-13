package com.edukus.diabeto.persistence.repository;

import com.edukus.diabeto.persistence.entity.AppointmentEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity,Long> {

  List<AppointmentEntity> findAllByEmail(String email);
  void deleteByIdAndEmail(Long id, String email);

  AppointmentEntity findFirstByEmailAndDateGreaterThanOrderByDateAsc(String email, LocalDateTime date);

}
