package com.dmBackend.app.useCase.driver;

import com.dmBackend.app.dto.driver.DriverRequestDTO;
import com.dmBackend.app.dto.driver.DriverResponseDTO;

public interface CreateDriverUseCase {
    DriverResponseDTO execute(String userId, DriverRequestDTO request);
}