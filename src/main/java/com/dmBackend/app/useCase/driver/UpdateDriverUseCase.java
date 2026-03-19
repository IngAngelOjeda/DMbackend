package com.dmBackend.app.useCase.driver;

import com.dmBackend.app.dto.driver.DriverResponseDTO;
import com.dmBackend.app.dto.driver.DriverUpdateRequestDTO;

public interface UpdateDriverUseCase {
    DriverResponseDTO execute(String userId, Long driverId, DriverUpdateRequestDTO request);
}