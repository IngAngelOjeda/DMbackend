package com.dmBackend.domain.port.user;

import com.dmBackend.app.dto.auth.AuthRequestDTO;
import com.dmBackend.app.dto.auth.AuthResponseDTO;
import com.dmBackend.app.dto.auth.RefreshTokenRequestDTO;

public interface AuthService {

    AuthResponseDTO login(AuthRequestDTO request);

    AuthResponseDTO refresh(RefreshTokenRequestDTO request);

    void logout(String refreshToken);
}
