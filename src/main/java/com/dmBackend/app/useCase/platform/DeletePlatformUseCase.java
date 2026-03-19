package com.dmBackend.app.useCase.platform;

public interface DeletePlatformUseCase {
    void execute(String userId, Long platformId);
}