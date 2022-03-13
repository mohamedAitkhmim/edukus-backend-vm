package com.edukus.diabeto.service;

import com.edukus.diabeto.persistence.entity.AppSetting;
import com.edukus.diabeto.persistence.repository.AppSettingRepository;
import org.springframework.stereotype.Service;


@Service
public class AppSettingServiceImpl {

  AppSettingRepository appSettingRepository;

  public AppSettingServiceImpl(AppSettingRepository appSettingRepository) {
    this.appSettingRepository = appSettingRepository;
  }

  public AppSetting getAppSetting(String label) {

    return appSettingRepository.getAppSettingByLabel(label);
  }

}
