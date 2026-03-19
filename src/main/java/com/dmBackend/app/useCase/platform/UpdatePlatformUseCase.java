package com.dmBackend.app.useCase.platform;

import com.dmBackend.app.dto.platform.PlatformResponseDTO;
import com.dmBackend.app.dto.platform.PlatformUpdateRequestDTO;

public interface UpdatePlatformUseCase {
    PlatformResponseDTO execute(String userId, Long platformId, PlatformUpdateRequestDTO request);
}