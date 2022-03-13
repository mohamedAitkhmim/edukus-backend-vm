package com.edukus.diabeto.persistence.repository;

import com.edukus.diabeto.persistence.entity.AppSetting;
import org.springframework.data.repository.CrudRepository;

public interface AppSettingRepository extends CrudRepository<AppSetting, Long> {

  AppSetting getAppSettingByLabel(String label);

}
