package com.dmBackend.infra.adapters.driver;

import com.dmBackend.app.dto.driver.DriverRequestDTO;
import com.dmBackend.app.dto.driver.DriverResponseDTO;
import com.dmBackend.app.dto.driver.DriverUpdateRequestDTO;
import com.dmBackend.domain.model.DriverEntity;
import com.dmBackend.domain.port.driver.DriverService;
import com.dmBackend.infra.exception.BusinessException;
import com.dmBackend.infra.exception.ResourceNotFoundException;
import com.dmBackend.infra.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    @Override
    @Transactional
    public DriverResponseDTO create(String userId, DriverRequestDTO request) {
        if (request.getDocumentNumber() != null
                && driverRepository.existsByUserIdAndDocumentNumber(userId, request.getDocumentNumber())) {
            throw new BusinessException("A driver with document number '" + request.getDocumentNumber() + "' already exists");
        }

        DriverEntity driver = DriverEntity.builder()
                .userId(userId)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .documentNumber(request.getDocumentNumber())
                .phone(request.getPhone())
                .build();

        return toResponse(driverRepository.save(driver));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DriverResponseDTO> getAll(String userId, Pageable pageable) {
        return driverRepository.findByUserId(userId, pageable).map(this::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public DriverResponseDTO getById(String userId, Long driverId) {
        return toResponse(findOwnedDriver(userId, driverId));
    }

    @Override
    @Transactional
    public DriverResponseDTO update(String userId, Long driverId, DriverUpdateRequestDTO request) {
        DriverEntity driver = findOwnedDriver(userId, driverId);

        if (request.getFirstName() != null) driver.setFirstName(request.getFirstName());
        if (request.getLastName() != null) driver.setLastName(request.getLastName());
        if (request.getDocumentNumber() != null) driver.setDocumentNumber(request.getDocumentNumber());
        if (request.getPhone() != null) driver.setPhone(request.getPhone());
        if (request.getActive() != null) driver.setActive(request.getActive());

        return toResponse(driverRepository.save(driver));
    }

    @Override
    @Transactional
    public void delete(String userId, Long driverId) {
        DriverEntity driver = findOwnedDriver(userId, driverId);
        driver.setActive(false);
        driverRepository.save(driver);
    }

    private DriverEntity findOwnedDriver(String userId, Long driverId) {
        return driverRepository.findByIdAndUserId(driverId, userId)
                .orElseThrow(() -> ResourceNotFoundException.of("Driver", driverId));
    }

    private DriverResponseDTO toResponse(DriverEntity d) {
        return DriverResponseDTO.builder()
                .id(d.getId())
                .firstName(d.getFirstName())
                .lastName(d.getLastName())
                .documentNumber(d.getDocumentNumber())
                .phone(d.getPhone())
                .active(d.isActive())
                .createdAt(d.getCreatedAt())
                .updatedAt(d.getUpdatedAt())
                .build();
    }
}