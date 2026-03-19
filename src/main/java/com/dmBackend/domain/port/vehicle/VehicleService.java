package com.dmBackend.domain.port.vehicle;

import com.dmBackend.app.dto.vehicle.VehicleRequestDTO;
import com.dmBackend.app.dto.vehicle.VehicleResponseDTO;
import com.dmBackend.app.dto.vehicle.VehicleUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleService {

    VehicleResponseDTO create(String userId, VehicleRequestDTO request);

    Page<VehicleResponseDTO> getAll(String userId, Pageable pageable);

    VehicleResponseDTO getById(String userId, Long vehicleId);

    VehicleResponseDTO update(String userId, Long vehicleId, VehicleUpdateRequestDTO request);

    void delete(String userId, Long vehicleId);
}