package com.dmBackend.infra.adapters.Keycloak;

import com.dmBackend.app.dto.auth.AuthRequestDTO;
import com.dmBackend.app.dto.auth.AuthResponseDTO;
import com.dmBackend.app.dto.auth.RefreshTokenRequestDTO;
import com.dmBackend.infra.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class KeycloakAuthAdapter {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    public AuthResponseDTO login(AuthRequestDTO request) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "password");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("username", request.getUsername());
        form.add("password", request.getPassword());

        return toAuthResponse(callTokenEndpoint(form));
    }

    public AuthResponseDTO refresh(RefreshTokenRequestDTO request) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "refresh_token");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("refresh_token", request.getRefreshToken());

        return toAuthResponse(callTokenEndpoint(form));
    }

    public void logout(String refreshToken) {
        String logoutUrl = authServerUrl + "/realms/" + realm + "/protocol/openid-connect/logout";

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("refresh_token", refreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        try {
            restTemplate.postForEntity(logoutUrl, new HttpEntity<>(form, headers), Void.class);
            log.info("Keycloak logout successful");
        } catch (HttpClientErrorException e) {
            log.error("Keycloak logout failed: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new BusinessException("Logout failed: invalid or expired refresh token");
        }
    }

    private KeycloakTokenResponse callTokenEndpoint(MultiValueMap<String, String> form) {
        String tokenUrl = authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        try {
            ResponseEntity<KeycloakTokenResponse> response =
                    restTemplate.postForEntity(tokenUrl, new HttpEntity<>(form, headers), KeycloakTokenResponse.class);

            if (response.getBody() == null) {
                throw new BusinessException("Empty response from Keycloak token endpoint");
            }
            return response.getBody();

        } catch (HttpClientErrorException e) {
            log.error("Keycloak token request failed: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new BusinessException("Invalid credentials");
            }
            throw new BusinessException("Authentication failed: " + e.getStatusCode());
        }
    }

    private AuthResponseDTO toAuthResponse(KeycloakTokenResponse r) {
        return AuthResponseDTO.builder()
                .accessToken(r.getAccessToken())
                .refreshToken(r.getRefreshToken())
                .expiresIn(r.getExpiresIn())
                .refreshExpiresIn(r.getRefreshExpiresIn())
                .tokenType(r.getTokenType())
                .build();
    }
}