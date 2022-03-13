package com.edukus.diabeto.presentation;

import com.edukus.diabeto.presentation.dto.MeasureDto;
import com.edukus.diabeto.presentation.dto.MeasureTypeDto;
import com.edukus.diabeto.security.TokenParserUtil;
import com.edukus.diabeto.service.MeasureService;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import java.util.stream.Collectors;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeasureController {

  private MeasureService measureService;

  public MeasureController(MeasureService measureService) {
    this.measureService = measureService;
  }

  @PostMapping("/private/measure/{type}")
  public ResponseEntity<Void> saveMeasure(@PathVariable String type, @RequestBody MeasureDto measureDto, Principal userPrincipal) {
    String preferredUsername = TokenParserUtil.extractToken(userPrincipal).getPreferredUsername();
    measureService.saveMeasure(preferredUsername, type, measureDto);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  }

  @GetMapping("/private/measure/{type}")
  public ResponseEntity<Object> findMeasure(@PathVariable String type, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate, Principal userPrincipal)
  {
    String preferredUsername = TokenParserUtil.extractToken(userPrincipal).getPreferredUsername();
    if (endDate == null) {
      return ResponseEntity.status(HttpStatus.OK).body(measureService.findMeasures(preferredUsername, type, startDate, startDate));
    }
    return ResponseEntity.status(HttpStatus.OK).body(measureService.findMeasures(preferredUsername, type, startDate, endDate).stream().collect(Collectors.groupingBy(MeasureDto::getDate)).entrySet());

  }

  @GetMapping("/private/last-measure/{type}")
  public ResponseEntity<MeasureDto> findLastMeasure(@PathVariable String type, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date, Principal userPrincipal) {
    String preferredUsername = TokenParserUtil.extractToken(userPrincipal).getPreferredUsername();
    return ResponseEntity.status(HttpStatus.OK).body(measureService.findLastMeasure(preferredUsername, type, date));
  }

  @DeleteMapping("/private/measure/{id}")
  public ResponseEntity<Object> deleteMeasure(@PathVariable Long id, Principal userPrincipal) {
    String preferredUsername = TokenParserUtil.extractToken(userPrincipal).getPreferredUsername();
    measureService.deleteMeasure(preferredUsername, id);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  }

  @GetMapping("/public/measure-types")
  public ResponseEntity<List<MeasureTypeDto>> getMeasureTypes() {
    return ResponseEntity.status(HttpStatus.OK).body(measureService.getMeasureTypes());
  }

}
