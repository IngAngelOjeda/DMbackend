package com.dmBackend.app.dto.metrics;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DriverKilometersDTO {
    private Long driverId;
    private String fullName;
    private Long kilometers;
}
