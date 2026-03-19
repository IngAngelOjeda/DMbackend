package com.dmBackend.app.useCase.driver;

import com.dmBackend.app.dto.driver.DriverResponseDTO;

public interface GetDriverByIdUseCase {
    DriverResponseDTO execute(String userId, Long driverId);
}