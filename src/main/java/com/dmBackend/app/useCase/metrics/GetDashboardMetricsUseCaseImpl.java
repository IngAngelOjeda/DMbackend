package com.dmBackend.app.useCase.metrics;

import com.dmBackend.app.dto.metrics.DashboardMetricsResponseDTO;
import com.dmBackend.domain.port.metrics.MetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetDashboardMetricsUseCaseImpl implements GetDashboardMetricsUseCase {

    private final MetricsService metricsService;

    @Override
    public DashboardMetricsResponseDTO execute(String userId) {
        return metricsService.getDashboard(userId);
    }
}
