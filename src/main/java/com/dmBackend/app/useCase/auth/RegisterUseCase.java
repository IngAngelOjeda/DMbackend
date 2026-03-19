package com.dmBackend.app.useCase.auth;

import com.dmBackend.app.dto.auth.RegisterRequestDTO;
import com.dmBackend.app.dto.user.UserResponseDTO;

public interface RegisterUseCase {
    UserResponseDTO execute(RegisterRequestDTO request);
}