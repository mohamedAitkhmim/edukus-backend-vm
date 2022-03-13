package com.edukus.diabeto.service.mapper;

import com.edukus.diabeto.persistence.entity.MedicineTimeEntity;
import com.edukus.diabeto.presentation.dto.MedicineDto;
import com.edukus.diabeto.presentation.dto.MedicineTimeDto;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;

public interface MedicineTimeMapper {

  static List<MedicineTimeEntity> toEntities(MedicineDto dto, Long id) {
    if (dto == null) {
      return Collections.emptyList();
    }
    return CollectionUtils.emptyIfNull(dto.getTimes()).stream().map(time -> new MedicineTimeEntity(id, time, dto.getNotification())).collect(Collectors.toList());
  }

  static List<MedicineTimeDto> toDtos(List<MedicineTimeEntity> entities) {
    return CollectionUtils.emptyIfNull(entities).stream().map(entity -> new MedicineTimeDto(entity.getTime(), entity.isNotification())).collect(Collectors.toList());
  }

  static MedicineTimeDto toDto(LocalDateTime time, boolean notification) {
    if (time == null) {
      return null;
    }
    return new MedicineTimeDto(time, notification);
  }


}
