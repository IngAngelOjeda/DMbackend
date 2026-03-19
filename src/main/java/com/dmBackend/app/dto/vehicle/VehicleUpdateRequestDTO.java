package com.dmBackend.app.dto.vehicle;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleUpdateRequestDTO {

    private String brand;
    private String model;
    private String plate;

    @Min(value = 1900, message = "Year must be greater than 1900")
    @Max(value = 2100, message = "Year must be less than 2100")
    private Integer yearModel;

    private Boolean active;
}