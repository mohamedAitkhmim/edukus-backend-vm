package com.edukus.diabeto.security.keycloak;

import com.edukus.diabeto.security.keycloak.dto.TokenDto;

public interface UserKeycloak {

  TokenDto logWithKeycloak(String username, String password) throws RuntimeException;

  TokenDto refreshToken(String refreshToken) throws RuntimeException;

}