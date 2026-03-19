package com.dmBackend.app.useCase.driver;

import com.dmBackend.app.dto.driver.DriverRequestDTO;
import com.dmBackend.app.dto.driver.DriverResponseDTO;
import com.dmBackend.domain.port.driver.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateDriverUseCaseImpl implements CreateDriverUseCase {

    private final DriverService driverService;

    @Override
    public DriverResponseDTO execute(String userId, DriverRequestDTO request) {
        return driverService.create(userId, request);
    }
}