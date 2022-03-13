package com.edukus.diabeto.service.exception;

public class ProfileException extends RuntimeException{

  public static final String PROFILE_NOT_FOUND = "profile not found";

  public ProfileException(String message) {
    super(message);
  }

  public static ProfileException create(String message){
    return new ProfileException(message);
  }
}
