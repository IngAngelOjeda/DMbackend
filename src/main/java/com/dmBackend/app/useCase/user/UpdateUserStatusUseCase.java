package com.dmBackend.app.useCase.user;

import com.dmBackend.app.dto.user.UserStatusUpdateDTO;
import com.dmBackend.app.dto.user.UserUpdateResponseDTO;

public interface UpdateUserStatusUseCase {
    UserUpdateResponseDTO execute(String id, UserStatusUpdateDTO request);
}
