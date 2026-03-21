package com.dmBackend.app.useCase.metrics;

import com.dmBackend.app.dto.metrics.DashboardMetricsResponseDTO;

public interface GetDashboardMetricsUseCase {
    DashboardMetricsResponseDTO execute(String userId);
}
