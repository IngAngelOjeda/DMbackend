package com.dmBackend.app.useCase.auth;

import com.dmBackend.app.dto.auth.AuthResponseDTO;
import com.dmBackend.app.dto.auth.RefreshTokenRequestDTO;

public interface RefreshTokenUseCase {
    AuthResponseDTO execute(RefreshTokenRequestDTO request);
}