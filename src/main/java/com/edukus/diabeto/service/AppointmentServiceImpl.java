package com.edukus.diabeto.service;

import com.edukus.diabeto.persistence.entity.AppointmentEntity;
import com.edukus.diabeto.persistence.repository.AppointmentRepository;
import com.edukus.diabeto.presentation.dto.AppointmentDto;
import com.edukus.diabeto.service.mapper.AppointmentMapper;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService {

  @Autowired
  private AppointmentRepository appointmentRepository;

  @Override
  public void saveAppointment(AppointmentDto appointmentDto, String email) {
    AppointmentEntity appointmentEntity = AppointmentMapper.toEntity(appointmentDto, email);
    appointmentRepository.save(appointmentEntity);
  }

  @Override
  public List<AppointmentDto> getAppointments(String email) {
    return AppointmentMapper.toDtos(appointmentRepository.findAllByEmail(email));
  }

  @Override
  @Transactional
  public void deleteAppointment(Long id, String email) {
    appointmentRepository.deleteByIdAndEmail(id, email);
  }

  @Override
  public AppointmentDto getNextAppointment(String email, LocalDateTime date) {
    return AppointmentMapper.toDto(appointmentRepository.findFirstByEmailAndDateGreaterThanOrderByDateAsc(email, date));
  }
}
