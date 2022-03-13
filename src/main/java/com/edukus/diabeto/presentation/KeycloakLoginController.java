package com.edukus.diabeto.presentation;

import com.edukus.diabeto.security.keycloak.UserKeycloak;
import com.edukus.diabeto.security.keycloak.dto.LoginDto;
import com.edukus.diabeto.security.keycloak.dto.TokenDto;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/public/login")
public class KeycloakLoginController {

  @Autowired
  UserKeycloak userKeycloak;

  @GetMapping("/access-token")
  public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {

    return ResponseEntity.status(HttpStatus.OK).body(userKeycloak.logWithKeycloak(loginDto.getUsername(), loginDto.getPassword()));

  }

  @GetMapping("/refresh-token")
  public ResponseEntity<TokenDto> refresh(@RequestBody TokenDto tokenDto) {

    return ResponseEntity.status(HttpStatus.OK).body(userKeycloak.refreshToken(tokenDto.getRefreshToken()));

  }


}
