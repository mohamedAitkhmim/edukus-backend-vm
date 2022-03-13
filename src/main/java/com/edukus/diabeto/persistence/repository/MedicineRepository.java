package com.edukus.diabeto.persistence.repository;

import com.edukus.diabeto.persistence.entity.MedicineEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<MedicineEntity,Long> {

  List<MedicineEntity> findAllByEmail(String email);
  void deleteAllByIdAndEmail(Long id, String email);
}
