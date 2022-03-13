package com.edukus.diabeto.service;

import com.edukus.diabeto.persistence.entity.MedicineEntity;
import com.edukus.diabeto.persistence.entity.MedicineTimeEntity;
import com.edukus.diabeto.persistence.repository.MedicineRepository;
import com.edukus.diabeto.persistence.repository.MedicineTimeRepository;
import com.edukus.diabeto.presentation.dto.MedicineDto;
import com.edukus.diabeto.service.mapper.MedicineMapper;
import com.edukus.diabeto.service.mapper.MedicineTimeMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class MedicineServiceImpl implements MedicineService {

  public static final String INTERNATIONAL_UNIT = "IU";
  @Autowired
  private MedicineTimeRepository medicineTimeRepository;
  @Autowired
  private MedicineRepository medicineRepository;

  @Override
  @Transactional
  public void saveMedicine(MedicineDto medicineDto, String email) {
    MedicineEntity medicineEntity = MedicineMapper.toEntity(medicineDto, email);
    medicineEntity = medicineRepository.save(medicineEntity);
    List<MedicineTimeEntity> timeEntityList = MedicineTimeMapper.toEntities(medicineDto, medicineEntity.getId());
    medicineTimeRepository.saveAll(timeEntityList);
  }

  @Override
  public List<MedicineDto> getAllMedicines(String email) {
    List<MedicineDto> allDtos = MedicineMapper.toDtos(medicineRepository.findAllByEmail(email));
    List<MedicineDto> withIU = allDtos.stream().filter((dto -> INTERNATIONAL_UNIT.equals(dto.getUnit()))).collect(Collectors.toList());
    List<MedicineDto> withoutIU= allDtos.stream().filter((dto -> !INTERNATIONAL_UNIT.equals(dto.getUnit()))).collect(Collectors.toList());
    return ListUtils.union(withIU, withoutIU);
  }

  @Override
  @Transactional
  public void deleteMedicine(Long id, String email) {
    medicineRepository.deleteAllByIdAndEmail(id, email);
  }

  @Override
  public MedicineDto getNextMedicine(String email, LocalDateTime date) {
    return MedicineMapper.toDto(medicineTimeRepository.findNextMedicineTime(date.toLocalTime(), email));
  }

}
