package com.dmBackend.app.useCase.platform;

import com.dmBackend.app.dto.platform.PlatformResponseDTO;
import com.dmBackend.domain.port.platform.PlatformService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAllPlatformsUseCaseImpl implements GetAllPlatformsUseCase {

    private final PlatformService platformService;

    @Override
    public Page<PlatformResponseDTO> execute(String userId, Pageable pageable) {
        return platformService.getAll(userId, pageable);
    }
}