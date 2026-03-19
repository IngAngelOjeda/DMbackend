package com.dmBackend.app.dto.session;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OpenSessionRequestDTO {

    @NotNull(message = "Driver ID is required")
    private Long driverId;

    @NotNull(message = "Vehicle ID is required")
    private Long vehicleId;

    @NotNull(message = "Platform ID is required")
    private Long platformId;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "Initial mileage is required")
    @Min(value = 0, message = "Initial mileage must be non-negative")
    private Integer initialMileage;

    @Min(value = 0, message = "Fuel level must be between 0 and 100")
    @Max(value = 100, message = "Fuel level must be between 0 and 100")
    private Integer initialFuelLevel;

    private String notes;
}