package com.dmBackend.app.useCase.driver;

import com.dmBackend.domain.port.driver.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteDriverUseCaseImpl implements DeleteDriverUseCase {

    private final DriverService driverService;

    @Override
    public void execute(String userId, Long driverId) {
        driverService.delete(userId, driverId);
    }
}