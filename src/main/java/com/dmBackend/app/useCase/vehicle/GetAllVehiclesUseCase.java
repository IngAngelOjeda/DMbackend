package com.dmBackend.app.useCase.vehicle;

import com.dmBackend.app.dto.vehicle.VehicleResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetAllVehiclesUseCase {
    Page<VehicleResponseDTO> execute(String userId, Pageable pageable);
}