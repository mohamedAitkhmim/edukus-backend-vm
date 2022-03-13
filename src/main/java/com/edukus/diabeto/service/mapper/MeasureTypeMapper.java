package com.edukus.diabeto.service.mapper;

import com.edukus.diabeto.persistence.entity.MeasureTypeEntity;
import com.edukus.diabeto.presentation.dto.MeasureTypeDto;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;

public class MeasureTypeMapper {

  public static List<MeasureTypeDto> fromEntities(List<MeasureTypeEntity> measureTypeEntityEntities) {
    return CollectionUtils.emptyIfNull(measureTypeEntityEntities).stream().map(MeasureTypeMapper::fromEntity).collect(Collectors.toList());
  }

  public static MeasureTypeDto fromEntity(MeasureTypeEntity entity) {
    if (entity == null) {
      return MeasureTypeDto.builder().build();
    }
    return MeasureTypeDto.builder().code(entity.getCode()).label(entity.getLabel()).unit(entity.getUnit()).min(entity.getMin()).max(entity.getMax()).minSystolic(entity.getMinSystolic())
        .maxSystolic(entity.getMaxSystolic()).minDiastolic(entity.getMinDiastolic()).maxDiastolic(entity.getMaxDiastolic()).minRefPrePrandial(entity.getMinRefPrePrandial())
        .maxRefPrePrandial(entity.getMaxRefPrePrandial()).minRefPostPrandial(entity.getMinRefPostPrandial()).maxRefPostPrandial(entity.getMaxRefPostPrandial()).build();
  }

}
