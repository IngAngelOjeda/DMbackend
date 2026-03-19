package com.dmBackend.infra.adapters.session;

import com.dmBackend.app.dto.session.CloseSessionRequestDTO;
import com.dmBackend.app.dto.session.OpenSessionRequestDTO;
import com.dmBackend.app.dto.session.SessionResponseDTO;
import com.dmBackend.domain.model.DriverEntity;
import com.dmBackend.domain.model.PlatformEntity;
import com.dmBackend.domain.model.SessionEntity;
import com.dmBackend.domain.model.VehicleEntity;
import com.dmBackend.domain.model.enums.SessionStatus;
import com.dmBackend.domain.port.session.SessionService;
import com.dmBackend.infra.exception.BusinessException;
import com.dmBackend.infra.exception.ResourceNotFoundException;
import com.dmBackend.infra.repository.DriverRepository;
import com.dmBackend.infra.repository.PlatformRepository;
import com.dmBackend.infra.repository.SessionRepository;
import com.dmBackend.infra.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;
    private final PlatformRepository platformRepository;

    @Override
    @Transactional
    public SessionResponseDTO open(String userId, OpenSessionRequestDTO request) {
        VehicleEntity vehicle = vehicleRepository.findByIdAndUserId(request.getVehicleId(), userId)
                .orElseThrow(() -> ResourceNotFoundException.of("Vehicle", request.getVehicleId()));

        DriverEntity driver = driverRepository.findByIdAndUserId(request.getDriverId(), userId)
                .orElseThrow(() -> ResourceNotFoundException.of("Driver", request.getDriverId()));

        PlatformEntity platform = platformRepository.findByIdAndUserId(request.getPlatformId(), userId)
                .orElseThrow(() -> ResourceNotFoundException.of("Platform", request.getPlatformId()));

        if (sessionRepository.existsByVehicleIdAndStatus(vehicle.getId(), SessionStatus.OPEN)) {
            throw new BusinessException("Vehicle '" + vehicle.getPlate() + "' already has an open session");
        }

        if (sessionRepository.existsByDriverIdAndStatus(driver.getId(), SessionStatus.OPEN)) {
            throw new BusinessException("Driver '" + driver.getFirstName() + " " + driver.getLastName() + "' already has an open session");
        }

        SessionEntity session = SessionEntity.builder()
                .userId(userId)
                .vehicle(vehicle)
                .driver(driver)
                .platform(platform)
                .startTime(request.getStartTime())
                .initialMileage(request.getInitialMileage())
                .initialFuelLevel(request.getInitialFuelLevel())
                .notes(request.getNotes())
                .status(SessionStatus.OPEN)
                .build();

        // After save, session has the relations already loaded in memory — no extra queries
        return toResponse(sessionRepository.save(session));
    }

    @Override
    @Transactional
    public SessionResponseDTO close(String userId, Long sessionId, CloseSessionRequestDTO request) {
        // Use JOIN FETCH to avoid lazy-loading when mapping to response
        SessionEntity session = sessionRepository.findByIdAndUserIdWithRelations(sessionId, userId)
                .orElseThrow(() -> ResourceNotFoundException.of("Session", sessionId));

        if (session.getStatus() != SessionStatus.OPEN) {
            throw new BusinessException("Session is already " + session.getStatus().name().toLowerCase());
        }

        if (request.getFinalMileage() < session.getInitialMileage()) {
            throw new BusinessException("Final mileage (" + request.getFinalMileage()
                    + ") cannot be less than initial mileage (" + session.getInitialMileage() + ")");
        }

        if (request.getEndTime().isBefore(session.getStartTime())) {
            throw new BusinessException("End time cannot be before start time");
        }

        session.setEndTime(request.getEndTime());
        session.setFinalMileage(request.getFinalMileage());
        session.setFinalFuelLevel(request.getFinalFuelLevel());
        session.setStatus(SessionStatus.CLOSED);

        if (request.getNotes() != null) {
            session.setNotes(request.getNotes());
        }

        return toResponse(sessionRepository.save(session));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SessionResponseDTO> getAll(String userId, Pageable pageable) {
        // Single query with JOIN FETCH — avoids N+1 (driver + vehicle + platform per session)
        List<SessionResponseDTO> content = sessionRepository
                .findByUserIdWithRelations(userId, pageable)
                .stream()
                .map(this::toResponse)
                .toList();

        long total = sessionRepository.countByUserId(userId);
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    @Transactional(readOnly = true)
    public SessionResponseDTO getById(String userId, Long sessionId) {
        return toResponse(sessionRepository.findByIdAndUserIdWithRelations(sessionId, userId)
                .orElseThrow(() -> ResourceNotFoundException.of("Session", sessionId)));
    }

    private SessionResponseDTO toResponse(SessionEntity s) {
        return SessionResponseDTO.builder()
                .id(s.getId())
                .driverId(s.getDriver().getId())
                .driverFullName(s.getDriver().getFirstName() + " " + s.getDriver().getLastName())
                .vehicleId(s.getVehicle().getId())
                .vehiclePlate(s.getVehicle().getPlate())
                .platformId(s.getPlatform().getId())
                .platformName(s.getPlatform().getName())
                .startTime(s.getStartTime())
                .endTime(s.getEndTime())
                .initialMileage(s.getInitialMileage())
                .finalMileage(s.getFinalMileage())
                .initialFuelLevel(s.getInitialFuelLevel())
                .finalFuelLevel(s.getFinalFuelLevel())
                .notes(s.getNotes())
                .status(s.getStatus())
                .createdAt(s.getCreatedAt())
                .updatedAt(s.getUpdatedAt())
                .build();
    }
}