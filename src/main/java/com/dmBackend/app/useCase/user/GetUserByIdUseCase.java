package com.dmBackend.app.useCase.user;

import com.dmBackend.app.dto.user.UserGetResponseDTO;

public interface GetUserByIdUseCase {
    UserGetResponseDTO execute(String id);
}
