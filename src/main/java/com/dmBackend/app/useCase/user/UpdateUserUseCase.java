package com.dmBackend.app.useCase.user;

import com.dmBackend.app.dto.user.UserUpdateRequestDTO;
import com.dmBackend.app.dto.user.UserUpdateResponseDTO;

public interface UpdateUserUseCase {
    UserUpdateResponseDTO execute(String userId, UserUpdateRequestDTO request);
}
