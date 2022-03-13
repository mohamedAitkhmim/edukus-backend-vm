package com.edukus.diabeto.security.keycloak;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.account.KeycloakRole;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

public class CustomKeycloakAuthenticationProvider extends KeycloakAuthenticationProvider {

  public CustomKeycloakAuthenticationProvider() {
    super();
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    KeycloakAuthenticationToken token = ((KeycloakAuthenticationToken) authentication);
    KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) token.getPrincipal();

    Base64 base64Url = new Base64(true);
    String tokenStringBody = new String(base64Url.decode(keycloakPrincipal.getKeycloakSecurityContext().getTokenString().split("\\.")[1]));
    String role;

    try {
      role = new JSONObject(tokenStringBody).getString("authorities");
    } catch (JSONException e) {
      throw new RuntimeException("authorities not found");
    }
    List<GrantedAuthority> grantedAuthorities = new ArrayList();
    grantedAuthorities.add(new KeycloakRole("ROLE_" + role));
    return new KeycloakAuthenticationToken(token.getAccount(), token.isInteractive(), grantedAuthorities);
  }

}
