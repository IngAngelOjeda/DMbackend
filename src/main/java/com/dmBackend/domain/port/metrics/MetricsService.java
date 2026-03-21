package com.dmBackend.domain.port.metrics;

import com.dmBackend.app.dto.metrics.DashboardMetricsResponseDTO;

public interface MetricsService {
    DashboardMetricsResponseDTO getDashboard(String userId);
}
