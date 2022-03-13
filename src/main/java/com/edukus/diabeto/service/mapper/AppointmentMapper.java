package com.edukus.diabeto.service.mapper;

import com.edukus.diabeto.persistence.entity.AppointmentEntity;
import com.edukus.diabeto.persistence.entity.NotificationEntity;
import com.edukus.diabeto.presentation.dto.AppointmentDto;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;

public interface AppointmentMapper {

  static AppointmentEntity toEntity(AppointmentDto dto, String email) {
    if (dto == null) {
      return null;
    }
    AppointmentEntity appointmentEntity = new AppointmentEntity(null, dto.getService(), dto.getLocation(), dto.getDateTime(), dto.getNote(), email, null);
    List<NotificationEntity> notificationEntities = dto.getNotifications().stream().map(notification -> new NotificationEntity(null, notification, appointmentEntity)).collect(Collectors.toList());
    appointmentEntity.setNotifications(notificationEntities);
    return appointmentEntity;
  }

  static List<AppointmentDto> toDtos(List<AppointmentEntity> entities) {
    return CollectionUtils.emptyIfNull(entities).stream().map(AppointmentMapper::toDto).collect(Collectors.toList());
  }

  static AppointmentDto toDto(AppointmentEntity entity) {
    if (entity == null) {
      return AppointmentDto.builder().build();
    }
    return AppointmentDto.builder().id(entity.getId()).service(entity.getService()).location(entity.getLocation()).dateTime(entity.getDate()).note(entity.getNote())
        .notifications(entity.getNotifications().stream().map(NotificationEntity::getTime).collect(Collectors.toList())).build();
  }

}
