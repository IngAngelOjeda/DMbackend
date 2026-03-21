package com.dmBackend.app.dto.metrics;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VehicleKilometersDTO {
    private Long vehicleId;
    private String plate;
    private String brand;
    private String model;
    private Long kilometers;
}
