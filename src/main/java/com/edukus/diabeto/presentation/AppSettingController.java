package com.edukus.diabeto.presentation;

import com.edukus.diabeto.persistence.entity.AppSetting;
import com.edukus.diabeto.service.AppSettingServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/public/setting")
public class AppSettingController {

  AppSettingServiceImpl appSettingServiceImpl;

  public AppSettingController(AppSettingServiceImpl appSettingServiceImpl) {
    this.appSettingServiceImpl = appSettingServiceImpl;
  }

  @GetMapping
  public ResponseEntity<AppSetting> appSettings(@RequestParam("label") String label) {
    return ResponseEntity.status(HttpStatus.OK).body(appSettingServiceImpl.getAppSetting(label));
  }
}
