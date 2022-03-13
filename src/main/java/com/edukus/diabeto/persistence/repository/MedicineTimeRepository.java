package com.edukus.diabeto.persistence.repository;

import com.edukus.diabeto.persistence.entity.MedicineProjection;
import com.edukus.diabeto.persistence.entity.MedicineTimeEntity;
import com.edukus.diabeto.persistence.entity.MedicineTimeEntityId;
import java.time.LocalTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineTimeRepository extends CrudRepository<MedicineTimeEntity, MedicineTimeEntityId> {

  @Query(value = "select m.label as label, m.qte as qte, m.unit as unit, m.prandial as prandial , mt.time as time, mt.notification as notification FROM medicine_time mt INNER JOIN medicine m ON m.id = mt.id WHERE TIME(time) >= :time AND email = :email order by TIME(time) limit 1 ", nativeQuery = true)
  MedicineProjection findNextMedicineTime(@Param("time") LocalTime time, @Param("email") String email);
}
