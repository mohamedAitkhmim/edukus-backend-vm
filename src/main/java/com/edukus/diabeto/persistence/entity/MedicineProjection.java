package com.edukus.diabeto.persistence.entity;

import java.time.LocalDateTime;

public interface MedicineProjection {

  boolean getNotification();
  LocalDateTime getTime();
  String getLabel();
  Double getQte();
  String getUnit();
  String getPrandial();
}
