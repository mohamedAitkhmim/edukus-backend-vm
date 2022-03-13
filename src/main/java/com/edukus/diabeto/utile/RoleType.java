package com.edukus.diabeto.utile;

public enum RoleType {
  ADMIN("R03"), DOCTOR("R02"), PATIENT("R01");

  private String value;

  RoleType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
