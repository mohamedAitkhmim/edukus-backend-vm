package com.edukus.diabeto.utile;

public enum MeasureTime {
  PREPRANDIAL("preprandial"), POSTPRANDIAL("postprandial");
  private String value;

  MeasureTime(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

}
