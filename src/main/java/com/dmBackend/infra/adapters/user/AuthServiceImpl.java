package com.dmBackend.infra.adapters.user;

import com.dmBackend.app.dto.auth.AuthRequestDTO;
import com.dmBackend.app.dto.auth.AuthResponseDTO;
import com.dmBackend.app.dto.auth.RefreshTokenRequestDTO;
import com.dmBackend.domain.port.user.AuthService;
import com.dmBackend.infra.adapters.Keycloak.KeycloakAuthAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final KeycloakAuthAdapter keycloakAuthAdapter;

    @Override
    public AuthResponseDTO login(AuthRequestDTO request) {
        return keycloakAuthAdapter.login(request);
    }

    @Override
    public AuthResponseDTO refresh(RefreshTokenRequestDTO request) {
        return keycloakAuthAdapter.refresh(request);
    }

    @Override
    public void logout(String refreshToken) {
        keycloakAuthAdapter.logout(refreshToken);
    }
}