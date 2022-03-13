package com.edukus.diabeto.presentation;

import com.edukus.diabeto.presentation.dto.BaseSignUpDto;
import com.edukus.diabeto.presentation.dto.UserDto;
import com.edukus.diabeto.security.keycloak.dto.TokenDto;
import com.edukus.diabeto.service.RegistrationServiceImpl;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/public/registration")
public class RegistrationController {


  RegistrationServiceImpl registrationServiceImpl;

  public RegistrationController(RegistrationServiceImpl registrationServiceImpl) {
    this.registrationServiceImpl = registrationServiceImpl;
  }

  @PostMapping("/sign-up")
  public ResponseEntity<UserDto> signUp(@RequestBody @Valid  BaseSignUpDto baseSignUpDto) {

    return ResponseEntity.status(HttpStatus.OK).body(registrationServiceImpl.register(baseSignUpDto, false));

  }

  @GetMapping("/verify/{userId}")
  public ResponseEntity<TokenDto> verifyEmail(@PathVariable String userId, @RequestParam String challenge) {

    return ResponseEntity.status(HttpStatus.OK).body(registrationServiceImpl.verifyEmail(userId,challenge));

  }

}

