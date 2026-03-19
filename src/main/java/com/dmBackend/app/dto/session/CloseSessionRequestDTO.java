package com.dmBackend.app.dto.session;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CloseSessionRequestDTO {

    @NotNull(message = "End time is required")
    private LocalDateTime endTime;

    @NotNull(message = "Final mileage is required")
    @Min(value = 0, message = "Final mileage must be non-negative")
    private Integer finalMileage;

    @Min(value = 0, message = "Fuel level must be between 0 and 100")
    @Max(value = 100, message = "Fuel level must be between 0 and 100")
    private Integer finalFuelLevel;

    private String notes;
}