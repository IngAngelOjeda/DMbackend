package com.dmBackend.app.useCase.driver;

import com.dmBackend.app.dto.driver.DriverResponseDTO;
import com.dmBackend.app.dto.driver.DriverUpdateRequestDTO;
import com.dmBackend.domain.port.driver.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateDriverUseCaseImpl implements UpdateDriverUseCase {

    private final DriverService driverService;

    @Override
    public DriverResponseDTO execute(String userId, Long driverId, DriverUpdateRequestDTO request) {
        return driverService.update(userId, driverId, request);
    }
}