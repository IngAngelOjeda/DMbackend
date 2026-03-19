package com.dmBackend.app.useCase.vehicle;

import com.dmBackend.app.dto.vehicle.VehicleRequestDTO;
import com.dmBackend.app.dto.vehicle.VehicleResponseDTO;

public interface CreateVehicleUseCase {
    VehicleResponseDTO execute(String userId, VehicleRequestDTO request);
}