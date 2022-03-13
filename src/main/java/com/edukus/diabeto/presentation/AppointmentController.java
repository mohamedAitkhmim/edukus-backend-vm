package com.edukus.diabeto.presentation;

import com.edukus.diabeto.presentation.dto.AppointmentDto;
import com.edukus.diabeto.security.TokenParserUtil;
import com.edukus.diabeto.service.AppointmentService;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppointmentController {

  private final AppointmentService appointmentService;

  public AppointmentController(AppointmentService appointmentService) {
    this.appointmentService = appointmentService;
  }

  @PostMapping("/private/appointment")
  public ResponseEntity<Void> saveAppointment(@RequestBody AppointmentDto medicineDto, Principal userPrincipal) {
    String email = TokenParserUtil.extractToken(userPrincipal).getPreferredUsername();
    appointmentService.saveAppointment(medicineDto, email);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  }

  @GetMapping("/private/appointments")
  public ResponseEntity<List<AppointmentDto>> getAppointments(Principal userPrincipal) {
    String email = TokenParserUtil.extractToken(userPrincipal).getPreferredUsername();
    return ResponseEntity.status(HttpStatus.OK).body(appointmentService.getAppointments(email));
  }

  @GetMapping("/private/next-appointment")
  public ResponseEntity<AppointmentDto> getNextAppointment(Principal userPrincipal, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
    String email = TokenParserUtil.extractToken(userPrincipal).getPreferredUsername();
    return ResponseEntity.status(HttpStatus.OK).body(appointmentService.getNextAppointment(email, date));
  }

  @DeleteMapping("/private/appointments/{id}")
  public ResponseEntity<Object> deleteAppointment(@PathVariable Long id, Principal userPrincipal) {
    String email = TokenParserUtil.extractToken(userPrincipal).getPreferredUsername();
    appointmentService.deleteAppointment(id, email);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  }

}
