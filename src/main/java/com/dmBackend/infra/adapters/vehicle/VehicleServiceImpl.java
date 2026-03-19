package com.dmBackend.infra.adapters.vehicle;

import com.dmBackend.app.dto.vehicle.VehicleRequestDTO;
import com.dmBackend.app.dto.vehicle.VehicleResponseDTO;
import com.dmBackend.app.dto.vehicle.VehicleUpdateRequestDTO;
import com.dmBackend.domain.model.VehicleEntity;
import com.dmBackend.domain.port.vehicle.VehicleService;
import com.dmBackend.infra.exception.BusinessException;
import com.dmBackend.infra.exception.ResourceNotFoundException;
import com.dmBackend.infra.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    @Override
    @Transactional
    public VehicleResponseDTO create(String userId, VehicleRequestDTO request) {
        if (vehicleRepository.existsByUserIdAndPlate(userId, request.getPlate())) {
            throw new BusinessException("A vehicle with plate '" + request.getPlate() + "' already exists");
        }

        VehicleEntity vehicle = VehicleEntity.builder()
                .userId(userId)
                .brand(request.getBrand())
                .model(request.getModel())
                .plate(request.getPlate().toUpperCase())
                .yearModel(request.getYearModel())
                .build();

        return toResponse(vehicleRepository.save(vehicle));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VehicleResponseDTO> getAll(String userId, Pageable pageable) {
        return vehicleRepository.findByUserId(userId, pageable).map(this::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public VehicleResponseDTO getById(String userId, Long vehicleId) {
        return toResponse(findOwnedVehicle(userId, vehicleId));
    }

    @Override
    @Transactional
    public VehicleResponseDTO update(String userId, Long vehicleId, VehicleUpdateRequestDTO request) {
        VehicleEntity vehicle = findOwnedVehicle(userId, vehicleId);

        if (request.getPlate() != null && !request.getPlate().equalsIgnoreCase(vehicle.getPlate())) {
            if (vehicleRepository.existsByUserIdAndPlateAndIdNot(userId, request.getPlate(), vehicleId)) {
                throw new BusinessException("A vehicle with plate '" + request.getPlate() + "' already exists");
            }
            vehicle.setPlate(request.getPlate().toUpperCase());
        }

        if (request.getBrand() != null) vehicle.setBrand(request.getBrand());
        if (request.getModel() != null) vehicle.setModel(request.getModel());
        if (request.getYearModel() != null) vehicle.setYearModel(request.getYearModel());
        if (request.getActive() != null) vehicle.setActive(request.getActive());

        return toResponse(vehicleRepository.save(vehicle));
    }

    @Override
    @Transactional
    public void delete(String userId, Long vehicleId) {
        VehicleEntity vehicle = findOwnedVehicle(userId, vehicleId);
        vehicle.setActive(false);
        vehicleRepository.save(vehicle);
    }

    private VehicleEntity findOwnedVehicle(String userId, Long vehicleId) {
        return vehicleRepository.findByIdAndUserId(vehicleId, userId)
                .orElseThrow(() -> ResourceNotFoundException.of("Vehicle", vehicleId));
    }

    private VehicleResponseDTO toResponse(VehicleEntity v) {
        return VehicleResponseDTO.builder()
                .id(v.getId())
                .brand(v.getBrand())
                .model(v.getModel())
                .plate(v.getPlate())
                .yearModel(v.getYearModel())
                .active(v.isActive())
                .createdAt(v.getCreatedAt())
                .updatedAt(v.getUpdatedAt())
                .build();
    }
}