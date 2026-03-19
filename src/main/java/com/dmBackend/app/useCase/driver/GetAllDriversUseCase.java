package com.dmBackend.app.useCase.driver;

import com.dmBackend.app.dto.driver.DriverResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetAllDriversUseCase {
    Page<DriverResponseDTO> execute(String userId, Pageable pageable);
}