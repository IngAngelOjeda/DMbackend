package com.dmBackend.app.dto.metrics;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DashboardMetricsResponseDTO {
    private long totalSessions;
    private long openSessions;
    private long closedSessions;
    private long cancelledSessions;
    private long totalKilometers;
    private long totalVehicles;
    private long totalDrivers;
    private List<VehicleKilometersDTO> topVehiclesByKilometers;
    private List<DriverKilometersDTO> topDriversByKilometers;
    private List<PlatformUsageDTO> sessionsByPlatform;
}
