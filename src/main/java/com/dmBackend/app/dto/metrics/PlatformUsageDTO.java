package com.dmBackend.app.dto.metrics;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlatformUsageDTO {
    private Long platformId;
    private String platformName;
    private Long sessionCount;
    private Long totalKilometers;
}
