package com.dmBackend.domain.port.driver;

import com.dmBackend.app.dto.driver.DriverRequestDTO;
import com.dmBackend.app.dto.driver.DriverResponseDTO;
import com.dmBackend.app.dto.driver.DriverUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DriverService {

    DriverResponseDTO create(String userId, DriverRequestDTO request);

    Page<DriverResponseDTO> getAll(String userId, Pageable pageable);

    DriverResponseDTO getById(String userId, Long driverId);

    DriverResponseDTO update(String userId, Long driverId, DriverUpdateRequestDTO request);

    void delete(String userId, Long driverId);
}