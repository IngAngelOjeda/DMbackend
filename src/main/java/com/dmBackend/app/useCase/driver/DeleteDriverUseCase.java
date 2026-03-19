package com.dmBackend.app.useCase.driver;

public interface DeleteDriverUseCase {
    void execute(String userId, Long driverId);
}