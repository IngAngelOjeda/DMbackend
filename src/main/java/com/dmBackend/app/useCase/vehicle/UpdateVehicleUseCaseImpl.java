package com.dmBackend.app.useCase.vehicle;

import com.dmBackend.app.dto.vehicle.VehicleResponseDTO;
import com.dmBackend.app.dto.vehicle.VehicleUpdateRequestDTO;
import com.dmBackend.domain.port.vehicle.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateVehicleUseCaseImpl implements UpdateVehicleUseCase {

    private final VehicleService vehicleService;

    @Override
    public VehicleResponseDTO execute(String userId, Long vehicleId, VehicleUpdateRequestDTO request) {
        return vehicleService.update(userId, vehicleId, request);
    }
}