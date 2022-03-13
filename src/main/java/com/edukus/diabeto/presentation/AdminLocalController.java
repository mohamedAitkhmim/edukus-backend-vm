package com.edukus.diabeto.presentation;

import static com.edukus.diabeto.service.RegistrationServiceImpl.ADMIN_ROLE;

import com.edukus.diabeto.presentation.dto.BaseSignUpDto;
import com.edukus.diabeto.presentation.dto.UserDto;
import com.edukus.diabeto.service.RegistrationServiceImpl;
import javax.validation.Valid;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/public/admin")
@Profile({"local | localdb"})
public class AdminLocalController {

  RegistrationServiceImpl registrationServiceImpl;

  public AdminLocalController(RegistrationServiceImpl registrationServiceImpl) {
    this.registrationServiceImpl = registrationServiceImpl;
  }

  @PostMapping("/sign-up")
  public ResponseEntity<UserDto> signUp(@RequestBody @Valid BaseSignUpDto baseSignUpDto) {
    baseSignUpDto.setRoleId(ADMIN_ROLE);
    return ResponseEntity.status(HttpStatus.OK).body(registrationServiceImpl.register(baseSignUpDto, true));

  }

}
