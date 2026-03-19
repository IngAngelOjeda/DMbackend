package com.dmBackend.app.useCase.vehicle;

import com.dmBackend.domain.port.vehicle.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteVehicleUseCaseImpl implements DeleteVehicleUseCase {

    private final VehicleService vehicleService;

    @Override
    public void execute(String userId, Long vehicleId) {
        vehicleService.delete(userId, vehicleId);
    }
}