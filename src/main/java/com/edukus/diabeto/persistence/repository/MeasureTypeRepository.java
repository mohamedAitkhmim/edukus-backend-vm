package com.edukus.diabeto.persistence.repository;

import com.edukus.diabeto.persistence.entity.MeasureTypeEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasureTypeRepository extends JpaRepository<MeasureTypeEntity, Long> {

  Optional<MeasureTypeEntity> findByCode(String code);

}
