package com.dmBackend.app.useCase.vehicle;

import com.dmBackend.app.dto.vehicle.VehicleResponseDTO;
import com.dmBackend.domain.port.vehicle.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetVehicleByIdUseCaseImpl implements GetVehicleByIdUseCase {

    private final VehicleService vehicleService;

    @Override
    public VehicleResponseDTO execute(String userId, Long vehicleId) {
        return vehicleService.getById(userId, vehicleId);
    }
}