package com.dmBackend.app.useCase.vehicle;

import com.dmBackend.app.dto.vehicle.VehicleResponseDTO;

public interface GetVehicleByIdUseCase {
    VehicleResponseDTO execute(String userId, Long vehicleId);
}