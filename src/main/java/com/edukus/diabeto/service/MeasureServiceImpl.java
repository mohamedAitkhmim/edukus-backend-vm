package com.edukus.diabeto.service;

import static java.util.Comparator.comparing;

import com.edukus.diabeto.persistence.entity.MeasureEntity;
import com.edukus.diabeto.persistence.entity.MeasureSubTypeEntity;
import com.edukus.diabeto.persistence.entity.MeasureTypeEntity;
import com.edukus.diabeto.persistence.repository.MeasureRepository;
import com.edukus.diabeto.persistence.repository.MeasureSubTypeRepository;
import com.edukus.diabeto.persistence.repository.MeasureTypeRepository;
import com.edukus.diabeto.presentation.dto.MeasureTypeDto;
import com.edukus.diabeto.service.mapper.MeasureTypeMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import com.edukus.diabeto.presentation.dto.MeasureDto;
import com.edukus.diabeto.service.mapper.MeasureMapper;
import org.springframework.stereotype.Service;

@Service
public class MeasureServiceImpl implements MeasureService {

  public static final String TENSION = "tension";
  public static final String IMC = "imc";
  public static final String DIASTOLIC = "diastolic";
  public static final String HEIGHT = "height";
  public static final String WEIGHT = "weight";
  public static final String SYSTOLIC = "systolic";

  private final MeasureTypeRepository measureTypeRepository;
  private final MeasureRepository measureRepository;
  private final MeasureSubTypeRepository measureSubTypeRepository;

  public MeasureServiceImpl(MeasureTypeRepository measureTypeRepository, MeasureRepository measureRepository, MeasureSubTypeRepository measureSubTypeRepository) {
    this.measureTypeRepository = measureTypeRepository;
    this.measureRepository = measureRepository;
    this.measureSubTypeRepository = measureSubTypeRepository;
  }

  @Override
  public void saveMeasure(String userId, String type, MeasureDto measureDto) {
    MeasureTypeEntity measureTypeEntity = measureTypeRepository.findByCode(type).orElseThrow(() -> new RuntimeException("measure type not found"));
    List<MeasureEntity> measureEntities;
    switch (measureTypeEntity.getCode()){
      case TENSION: measureEntities = Arrays.asList(MeasureMapper.toEntity(measureDto, measureDto.getDiastolic(), userId, measureTypeEntity.getId(), getMeasureSubTypeId(DIASTOLIC)), MeasureMapper.toEntity(measureDto, measureDto.getSystolic(), userId, measureTypeEntity.getId(), getMeasureSubTypeId(
          SYSTOLIC))); break;
      case IMC: measureEntities = Arrays.asList(MeasureMapper.toEntity(measureDto, measureDto.getHeight(), userId, measureTypeEntity.getId(), getMeasureSubTypeId(HEIGHT)), MeasureMapper.toEntity(measureDto, measureDto.getWeight(), userId, measureTypeEntity.getId(), getMeasureSubTypeId(
          WEIGHT))); break;
      default: measureEntities = Collections.singletonList(MeasureMapper.toEntity(measureDto, measureDto.getValue(), userId, measureTypeEntity.getId(), null));
    }
    measureRepository.saveAll(measureEntities);

  }

  private Long getMeasureSubTypeId(String code) {
    return measureSubTypeRepository.findByCode(code).map(MeasureSubTypeEntity::getId).orElseThrow(() -> new IllegalArgumentException("subtype not found"));
  }

  @Override
  public List<MeasureDto> findMeasures(String userId, String measureType, LocalDateTime startDate, LocalDateTime endDate) {
    return new ArrayList<>(
        MeasureMapper.fromEntities(measureRepository.findByMeasureTypeAndDate(userId, measureType, startDate.toLocalDate().atStartOfDay(), endDate.plusDays(1).toLocalDate().atStartOfDay())).stream()
            .collect(Collectors.groupingBy(MeasureDto::getDateTime)).entrySet().stream().collect(Collectors.toMap(Entry::getKey, entry -> groupMeasures(entry.getValue()))).values()).stream().sorted(
        comparing(MeasureDto::getDateTime)).collect(Collectors.toList());
  }

  private MeasureDto groupMeasures(List<MeasureDto> dtos) {
    if(dtos == null || dtos.isEmpty()){
      return null;
    }
    MeasureDto dto = dtos.get(0);

    if(dtos.size()>1){
      dto.setValue(null);
      dto.setHeight(dto.getHeight() == null ? dtos.get(1).getHeight() : dto.getHeight());
      dto.setWeight(dto.getWeight() == null ? dtos.get(1).getWeight() : dto.getWeight());
      dto.setSystolic(dto.getSystolic() == null ? dtos.get(1).getSystolic() : dto.getSystolic());
      dto.setDiastolic(dto.getDiastolic() == null ? dtos.get(1).getDiastolic() : dto.getDiastolic());
    }
    return dto;
  }

  @Override
  public List<MeasureTypeDto> getMeasureTypes() {
    return MeasureTypeMapper.fromEntities(measureTypeRepository.findAll());
  }

  @Override
  @Transactional
  public void deleteMeasure(String email, Long id) {
    MeasureEntity measureEntity = measureRepository.findByMeasureAndEmail(id, email).orElseThrow(() -> new IllegalArgumentException("measure not found"));
    measureRepository.deleteByDateAndTypeAndEmail(measureEntity.getDateTime(), measureEntity.getMeasureTypeId(), email);
  }

  @Override
  public MeasureDto findLastMeasure(String preferredUsername, String type, LocalDateTime date) {
    LocalDateTime lastInsertionDate = measureRepository.findLastInsertionDate(date, type, preferredUsername);
    if(lastInsertionDate == null){
      return null;
    }
    return findMeasures(preferredUsername, type, lastInsertionDate, lastInsertionDate).stream().max(comparing(MeasureDto::getDateTime)).orElse(null);

  }
}
