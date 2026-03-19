package com.dmBackend.app.useCase.platform;

import com.dmBackend.app.dto.platform.PlatformRequestDTO;
import com.dmBackend.app.dto.platform.PlatformResponseDTO;

public interface CreatePlatformUseCase {
    PlatformResponseDTO execute(String userId, PlatformRequestDTO request);
}