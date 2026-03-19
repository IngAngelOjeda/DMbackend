package com.dmBackend.app.useCase.driver;

import com.dmBackend.app.dto.driver.DriverResponseDTO;
import com.dmBackend.domain.port.driver.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAllDriversUseCaseImpl implements GetAllDriversUseCase {

    private final DriverService driverService;

    @Override
    public Page<DriverResponseDTO> execute(String userId, Pageable pageable) {
        return driverService.getAll(userId, pageable);
    }
}