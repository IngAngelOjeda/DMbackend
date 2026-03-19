package com.dmBackend.app.useCase.auth;

import com.dmBackend.app.dto.auth.AuthRequestDTO;
import com.dmBackend.app.dto.auth.AuthResponseDTO;

public interface LoginUseCase {
    AuthResponseDTO execute(AuthRequestDTO request);
}