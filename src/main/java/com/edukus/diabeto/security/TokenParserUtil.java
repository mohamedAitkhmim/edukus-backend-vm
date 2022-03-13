package com.edukus.diabeto.security;

import java.security.Principal;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;

public class TokenParserUtil {

  public static AccessToken extractToken(Principal principal) {
    return ((KeycloakPrincipal) ((KeycloakAuthenticationToken) principal).getPrincipal()).getKeycloakSecurityContext().getToken();
  }

}
