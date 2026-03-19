package com.dmBackend.app.useCase.vehicle;

import com.dmBackend.app.dto.vehicle.VehicleResponseDTO;
import com.dmBackend.domain.port.vehicle.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAllVehiclesUseCaseImpl implements GetAllVehiclesUseCase {

    private final VehicleService vehicleService;

    @Override
    public Page<VehicleResponseDTO> execute(String userId, Pageable pageable) {
        return vehicleService.getAll(userId, pageable);
    }
}