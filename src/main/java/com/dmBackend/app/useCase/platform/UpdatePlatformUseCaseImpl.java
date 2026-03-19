package com.dmBackend.app.useCase.platform;

import com.dmBackend.app.dto.platform.PlatformResponseDTO;
import com.dmBackend.app.dto.platform.PlatformUpdateRequestDTO;
import com.dmBackend.domain.port.platform.PlatformService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdatePlatformUseCaseImpl implements UpdatePlatformUseCase {

    private final PlatformService platformService;

    @Override
    public PlatformResponseDTO execute(String userId, Long platformId, PlatformUpdateRequestDTO request) {
        return platformService.update(userId, platformId, request);
    }
}