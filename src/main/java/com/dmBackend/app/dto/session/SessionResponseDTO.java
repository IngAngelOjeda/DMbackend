package com.dmBackend.app.dto.session;

import com.dmBackend.domain.model.enums.SessionStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SessionResponseDTO {

    private Long id;
    private Long driverId;
    private String driverFullName;
    private Long vehicleId;
    private String vehiclePlate;
    private Long platformId;
    private String platformName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer initialMileage;
    private Integer finalMileage;
    private Integer initialFuelLevel;
    private Integer finalFuelLevel;
    private String notes;
    private SessionStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}