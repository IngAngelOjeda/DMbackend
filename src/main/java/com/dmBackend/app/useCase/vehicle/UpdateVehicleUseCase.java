package com.dmBackend.app.useCase.vehicle;

import com.dmBackend.app.dto.vehicle.VehicleResponseDTO;
import com.dmBackend.app.dto.vehicle.VehicleUpdateRequestDTO;

public interface UpdateVehicleUseCase {
    VehicleResponseDTO execute(String userId, Long vehicleId, VehicleUpdateRequestDTO request);
}