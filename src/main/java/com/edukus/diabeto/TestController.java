package com.edukus.diabeto;

import com.edukus.diabeto.security.TokenParserUtil;
import com.edukus.diabeto.utile.EmailSender;
import com.edukus.diabeto.utile.JasperByCollectionBeanData;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.jasperreports.engine.JRException;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.IDToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @Autowired
  EmailSender emailSender;

  @Autowired
  JasperByCollectionBeanData jasper;

  @Value("${edukus.mail.subject}")
  private String emailSubject;
  @Value("${edukus.mail.content}")
  private String emailContent;

  @GetMapping("/public/test")
  public String getPublicTest() {
    emailSender.sendEmail("yziani.work@gmail.com", emailSubject, emailContent);
    return "done";
  }

  @PostMapping("/public/test")
  public String postPublicTest() {

    return "public post test valid";
  }

    @GetMapping("/private/patient/test/{type}")
  public String getPrivatePatientTest(Principal userPrincipal, @PathVariable String type, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) throws IOException, JRException
    {
    String preferredUsername = TokenParserUtil.extractToken(userPrincipal).getPreferredUsername();

      jasper.generateReport(preferredUsername, type, startDate, endDate);

    return "done";
  }

  @GetMapping("/private/doctor/test")
  public String getPrivateDoctorTest(Principal userPrincipal) {

    String preferredUsername = TokenParserUtil.extractToken(userPrincipal).getPreferredUsername();

    return "private get doctor test valid";
  }

  @PostMapping("/post/test")
  public String postPrivateTest() {
    return "private post test valid";
  }


}
