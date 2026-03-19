package com.dmBackend.app.useCase.user;

import com.dmBackend.app.dto.user.UserRequestDTO;
import com.dmBackend.app.dto.user.UserResponseDTO;

public interface CreateUserUseCase {
    UserResponseDTO execute(UserRequestDTO request);
}
