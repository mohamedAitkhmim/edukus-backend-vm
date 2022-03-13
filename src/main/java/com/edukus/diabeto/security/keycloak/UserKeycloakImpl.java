package com.edukus.diabeto.security.keycloak;

import com.edukus.diabeto.persistence.entity.Registration;
import com.edukus.diabeto.persistence.repository.RegistrationRepository;
import com.edukus.diabeto.security.keycloak.dto.TokenDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserKeycloakImpl implements UserKeycloak {

  @Value("${keycloak.resource}")
  private String clientId;
  @Value("${keycloak.credentials.secret}")
  private String clientSecret;
  @Value("${edukus.keycloak.token-store}")
  private String keycloakUri;

  @Autowired
  private RegistrationRepository registrationRepository;

  @Override
  public TokenDto logWithKeycloak(String username, String password) throws RuntimeException {

    try {
      String uri = keycloakUri;

      HttpClient client = HttpClientBuilder.create().build();
      HttpPost post = new HttpPost(uri);
      List<BasicNameValuePair> urlParameters = new ArrayList<>();
      urlParameters.add(new BasicNameValuePair("grant_type", "password"));
      urlParameters.add(new BasicNameValuePair("client_id", clientId));
      urlParameters.add(new BasicNameValuePair("client_secret", clientSecret));
      urlParameters.add(new BasicNameValuePair("username", username));
      urlParameters.add(new BasicNameValuePair("password", password));
      post.setEntity(new UrlEncodedFormEntity(urlParameters));
      HttpResponse response = client.execute(post);
      BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
      StringBuffer result = new StringBuffer();
      String line1 = "";
      while ((line1 = rd.readLine()) != null) {
        result.append(line1);
      }

      if (response.getStatusLine().getStatusCode() != 200) {

        throw new RuntimeException(response.toString());

      }

      ObjectMapper mapper = new ObjectMapper();
      Map<String, String> map = mapper.readValue(result.toString(), Map.class);

      TokenDto tokenDto = new TokenDto(map.get("access_token"), map.get("refresh_token"));
      return tokenDto;

    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }

  }

  @Override
  public TokenDto refreshToken(String refreshToken) throws RuntimeException {

    Base64 base64Url = new Base64(true);

    String tokenBody = new String(base64Url.decode(refreshToken.split("\\.")[1]));
    String email;

    try {
      String sub = new JSONObject(tokenBody).getString("sub");
      email =  sub.split(":")[2];

    } catch (Exception e) {
      throw new RuntimeException("token not valid : enable to get phone from token");
    }

    Registration registration = registrationRepository.findByUser_Email(email).orElseThrow(() -> new RuntimeException("user not found"));
    String refreshTokenHash = refreshToken.split("\\.")[2];

    if (!refreshTokenHash.equals(registration.getTokenHash())) {
      throw new RuntimeException("refresh token invalidated");
    }

    try {
      String uri = keycloakUri;

      HttpClient client = HttpClientBuilder.create().build();
      HttpPost post = new HttpPost(uri);
      List<BasicNameValuePair> urlParameters = new ArrayList<>();
      urlParameters.add(new BasicNameValuePair("grant_type", "refresh_token"));
      urlParameters.add(new BasicNameValuePair("client_id", clientId));
      urlParameters.add(new BasicNameValuePair("client_secret", clientSecret));
      urlParameters.add(new BasicNameValuePair("refresh_token", refreshToken));
      post.setEntity(new UrlEncodedFormEntity(urlParameters));
      HttpResponse response = client.execute(post);
      BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
      StringBuffer result = new StringBuffer();
      String line1 = "";
      while ((line1 = rd.readLine()) != null) {
        result.append(line1);
      }

      if (response.getStatusLine().getStatusCode() != 200) {
        throw new RuntimeException(response.toString());
      }

      ObjectMapper mapper = new ObjectMapper();
      @SuppressWarnings("unchecked") Map<String, String> map = mapper.readValue(result.toString(), Map.class);

      String newAccessToken = map.get("access_token");
      String newRefreshToken = map.get("refresh_token");

      String newRefreshTokenHash = newRefreshToken.split("\\.")[2];

      registration.setTokenHash(newRefreshTokenHash);
      registrationRepository.save(registration);

      return new TokenDto(newAccessToken, newRefreshToken);

    } catch (Exception e) {
      throw new RuntimeException(e.getCause());
    }

  }

}
