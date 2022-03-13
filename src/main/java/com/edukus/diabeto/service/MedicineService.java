package com.edukus.diabeto.service;

import com.edukus.diabeto.presentation.dto.MedicineDto;
import java.time.LocalDateTime;
import java.util.List;

public interface MedicineService {

  void saveMedicine(MedicineDto medicineDto, String email);

  List<MedicineDto> getAllMedicines(String email);

  void deleteMedicine(Long id, String email);

  MedicineDto getNextMedicine(String email, LocalDateTime date);
}
