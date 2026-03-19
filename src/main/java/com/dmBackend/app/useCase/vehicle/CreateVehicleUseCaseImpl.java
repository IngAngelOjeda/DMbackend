package com.dmBackend.app.useCase.vehicle;

import com.dmBackend.app.dto.vehicle.VehicleRequestDTO;
import com.dmBackend.app.dto.vehicle.VehicleResponseDTO;
import com.dmBackend.domain.port.vehicle.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateVehicleUseCaseImpl implements CreateVehicleUseCase {

    private final VehicleService vehicleService;

    @Override
    public VehicleResponseDTO execute(String userId, VehicleRequestDTO request) {
        return vehicleService.create(userId, request);
    }
}