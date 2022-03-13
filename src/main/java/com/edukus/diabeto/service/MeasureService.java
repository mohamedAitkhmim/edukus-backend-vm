package com.edukus.diabeto.service;

import com.edukus.diabeto.presentation.dto.MeasureDto;

import com.edukus.diabeto.presentation.dto.MeasureTypeDto;
import java.time.LocalDateTime;
import java.util.List;

public interface MeasureService {

  void saveMeasure(String id, String measureType, MeasureDto measureDto);

  List<MeasureDto> findMeasures(String id, String measureType, LocalDateTime startDate, LocalDateTime date);

  List<MeasureTypeDto> getMeasureTypes();

  void deleteMeasure(String preferredUsername, Long id);

  MeasureDto findLastMeasure(String preferredUsername, String type, LocalDateTime date);
}
