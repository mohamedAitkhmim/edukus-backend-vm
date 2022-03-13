package com.edukus.diabeto.service.mapper;

import static com.edukus.diabeto.service.MeasureServiceImpl.DIASTOLIC;
import static com.edukus.diabeto.service.MeasureServiceImpl.HEIGHT;
import static com.edukus.diabeto.service.MeasureServiceImpl.SYSTOLIC;
import static com.edukus.diabeto.service.MeasureServiceImpl.WEIGHT;

import com.edukus.diabeto.persistence.entity.MeasureEntity;
import com.edukus.diabeto.persistence.entity.MeasureSubTypeEntity;
import com.edukus.diabeto.presentation.dto.MeasureDto;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class MeasureMapper {


  public static List<MeasureDto> fromEntities(List<MeasureEntity> measureEntities) {
    return CollectionUtils.emptyIfNull(measureEntities).stream().map(MeasureMapper::fromEntity).collect(Collectors.toList());
  }

  public static MeasureDto fromEntity(MeasureEntity measureEntity) {
    if (measureEntity == null) {
      return MeasureDto.builder().build();
    }
    return MeasureDto.builder()
        .id(measureEntity.getId())
        .dateTime(measureEntity.getDateTime())
        .reason(measureEntity.getReason())
        .value(measureEntity.getValue())
        .prandial(measureEntity.getPrandial())
        .height(getMeasureSubType(measureEntity, HEIGHT))
        .weight(getMeasureSubType(measureEntity, WEIGHT))
        .systolic(getMeasureSubType(measureEntity, SYSTOLIC))
        .diastolic(getMeasureSubType(measureEntity, DIASTOLIC))
        .build();
  }

  private static Double getMeasureSubType(MeasureEntity measureEntity, String code){
    MeasureSubTypeEntity measureSubTypeEntity = measureEntity.getMeasureSubTypeEntity();
    if (measureSubTypeEntity != null && code.equals(measureSubTypeEntity.getCode())){
      return measureEntity.getValue();
    }
    return null;
  }

  public static MeasureEntity toEntity(MeasureDto measureDto, Double value, String userId, Long type, Long subType) {
    if (measureDto == null) {
      return null;
    }
    return new MeasureEntity(measureDto.getId(), value, measureDto.getDateTime(), measureDto.getPrandial(), measureDto.getReason(), userId, type, subType, null, null, null);
  }
}
