package com.edukus.diabeto.service.mapper;

import com.edukus.diabeto.persistence.entity.MedicineEntity;
import com.edukus.diabeto.persistence.entity.MedicineProjection;
import com.edukus.diabeto.presentation.dto.MedicineDto;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;

public interface MedicineMapper {

  static MedicineEntity toEntity(MedicineDto dto, String email) {
    if (dto == null) {
      return null;
    }
    return new MedicineEntity(null, dto.getLabel(), dto.getQte(), dto.getUnit(), dto.getPrandial(), email, null);
  }

  static List<MedicineDto> toDtos(List<MedicineEntity> entities) {
    return CollectionUtils.emptyIfNull(entities).stream().map(MedicineMapper::toDto).collect(Collectors.toList());
  }

  static MedicineDto toDto(MedicineEntity entity) {
    if (entity == null) {
      return MedicineDto.builder().build();
    }
    return MedicineDto.builder().id(entity.getId()).label(entity.getLabel()).qte(entity.getQte()).unit(entity.getUnit()).prandial(entity.getPrandial())
        .times(MedicineTimeMapper.toDtos(entity.getTimes())).build();
  }

  static MedicineDto toDto(MedicineProjection projection) {
    if (projection == null) {
      return MedicineDto.builder().build();
    }
    return MedicineDto.builder().label(projection.getLabel()).qte(projection.getQte()).unit(projection.getUnit()).prandial(projection.getPrandial())
        .times(Collections.singletonList(MedicineTimeMapper.toDto(projection.getTime(), projection.getNotification()))).build();
  }
}
