package com.edukus.diabeto.service;

import com.edukus.diabeto.presentation.dto.AppointmentDto;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {

  void saveAppointment(AppointmentDto appointmentDto, String email);

  List<AppointmentDto> getAppointments(String email);

  void deleteAppointment(Long id, String email);
  AppointmentDto getNextAppointment(String email, LocalDateTime date);
}
