package com.edukus.diabeto.presentation;

import com.edukus.diabeto.security.TokenParserUtil;
import com.edukus.diabeto.utile.JasperByCollectionBeanData;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

  @Autowired
  JasperByCollectionBeanData jasper;

  @GetMapping("/private/patient/report/{type}")
  public ResponseEntity<byte[]> generateReport(Principal userPrincipal, @PathVariable String type, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) throws IOException, JRException
  {
    String preferredUsername = TokenParserUtil.extractToken(userPrincipal).getPreferredUsername();

    byte[] contents = jasper.generateReport(preferredUsername, type, startDate, endDate);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    String filename = "report.pdf";
    headers.setContentDispositionFormData(filename, filename);
    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
    return new ResponseEntity<>(contents, headers, HttpStatus.OK);
  }

}
