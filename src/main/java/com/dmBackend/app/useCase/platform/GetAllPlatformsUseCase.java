package com.dmBackend.app.useCase.platform;

import com.dmBackend.app.dto.platform.PlatformResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetAllPlatformsUseCase {
    Page<PlatformResponseDTO> execute(String userId, Pageable pageable);
}