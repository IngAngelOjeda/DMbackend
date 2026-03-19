package com.dmBackend.app.useCase.platform;

import com.dmBackend.app.dto.platform.PlatformRequestDTO;
import com.dmBackend.app.dto.platform.PlatformResponseDTO;
import com.dmBackend.domain.port.platform.PlatformService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreatePlatformUseCaseImpl implements CreatePlatformUseCase {

    private final PlatformService platformService;

    @Override
    public PlatformResponseDTO execute(String userId, PlatformRequestDTO request) {
        return platformService.create(userId, request);
    }
}