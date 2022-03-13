package com.edukus.diabeto.persistence.repository;

import com.edukus.diabeto.persistence.entity.MeasureSubTypeEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasureSubTypeRepository extends JpaRepository<MeasureSubTypeEntity, Long> {

  Optional<MeasureSubTypeEntity> findByCode(String code);

}
