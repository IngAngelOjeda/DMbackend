package com.dmBackend.app.useCase.platform;

import com.dmBackend.app.dto.platform.PlatformResponseDTO;
import com.dmBackend.domain.port.platform.PlatformService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetPlatformByIdUseCaseImpl implements GetPlatformByIdUseCase {

    private final PlatformService platformService;

    @Override
    public PlatformResponseDTO execute(String userId, Long platformId) {
        return platformService.getById(userId, platformId);
    }
}