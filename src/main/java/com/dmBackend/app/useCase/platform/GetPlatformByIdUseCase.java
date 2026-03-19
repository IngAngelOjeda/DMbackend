package com.dmBackend.app.useCase.platform;

import com.dmBackend.app.dto.platform.PlatformResponseDTO;

public interface GetPlatformByIdUseCase {
    PlatformResponseDTO execute(String userId, Long platformId);
}