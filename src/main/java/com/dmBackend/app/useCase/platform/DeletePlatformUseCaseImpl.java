package com.dmBackend.app.useCase.platform;

import com.dmBackend.domain.port.platform.PlatformService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletePlatformUseCaseImpl implements DeletePlatformUseCase {

    private final PlatformService platformService;

    @Override
    public void execute(String userId, Long platformId) {
        platformService.delete(userId, platformId);
    }
}