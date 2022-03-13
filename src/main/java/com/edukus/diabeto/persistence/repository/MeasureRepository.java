package com.edukus.diabeto.persistence.repository;

import com.edukus.diabeto.persistence.entity.MeasureEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MeasureRepository extends CrudRepository<MeasureEntity, Long> {

  @Query("select m from Measure m where m.measureTypeEntity.code = :measureType and m.user.email = :userId and m.dateTime >= :startDay and m.dateTime < :endDay")
  List<MeasureEntity> findByMeasureTypeAndDate(@Param("userId") String userId, @Param("measureType") String measureType, @Param("startDay") LocalDateTime startDay, @Param("endDay") LocalDateTime endDay);

  @Modifying
  @Query("delete from Measure m where m.dateTime = :date and m.measureTypeId = :type and m.user.email = :email ")
  void deleteByDateAndTypeAndEmail(@Param("date") LocalDateTime date, @Param("type") Long type, @Param("email") String email);

  @Query("select m from Measure m where m.id = :id and m.user.email = :email ")
  Optional<MeasureEntity> findByMeasureAndEmail(@Param("id") Long id, @Param("email") String email);

  @Query(value = "select distinct  m.date_time from measure m inner join measure_type mt on m.measure_type_id = mt.id\n"
      + "where m.email=:email and m.date_time <= :date and mt.code= :type order by m.date_time desc limit 1", nativeQuery = true)
  LocalDateTime findLastInsertionDate(@Param("date") LocalDateTime date, @Param("type") String type, @Param("email") String email);

}
