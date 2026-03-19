package com.dmBackend.app.useCase.user;

import com.dmBackend.app.dto.user.UserPageResponseDTO;
import org.springframework.data.domain.Pageable;

public interface GetAllUserUseCase {
    UserPageResponseDTO execute(String search, Pageable pageable);
}
