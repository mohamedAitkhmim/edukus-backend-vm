package com.edukus.diabeto.presentation;

import com.edukus.diabeto.presentation.dto.MedicineDto;
import com.edukus.diabeto.security.TokenParserUtil;
import com.edukus.diabeto.service.MedicineService;
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
public class MedicineController {

  private final MedicineService medicineService;

  public MedicineController(MedicineService medicineService) {
    this.medicineService = medicineService;
  }

  @PostMapping("/private/medicine")
  public ResponseEntity<Void> saveMedicine(@RequestBody MedicineDto medicineDto, Principal userPrincipal) {
    String email = TokenParserUtil.extractToken(userPrincipal).getPreferredUsername();
    medicineService.saveMedicine(medicineDto, email);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  }

  @GetMapping("/private/medicines")
  public ResponseEntity<List<MedicineDto>> getAllMedicines(Principal userPrincipal) {
    String email = TokenParserUtil.extractToken(userPrincipal).getPreferredUsername();
    return ResponseEntity.status(HttpStatus.OK).body(medicineService.getAllMedicines(email));
  }

  @GetMapping("/private/next-medicine")
  public ResponseEntity<MedicineDto> getNextMedicine(Principal userPrincipal, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
    String email = TokenParserUtil.extractToken(userPrincipal).getPreferredUsername();
    return ResponseEntity.status(HttpStatus.OK).body(medicineService.getNextMedicine(email, date));
  }


  @DeleteMapping("/private/medicines/{id}")
  public ResponseEntity<Object> deleteMedicine(@PathVariable Long id, Principal userPrincipal) {
    String email = TokenParserUtil.extractToken(userPrincipal).getPreferredUsername();
    medicineService.deleteMedicine(id, email);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  }

}
