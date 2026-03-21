package com.dmBackend.infra.controller.metrics;

import com.dmBackend.app.dto.metrics.DashboardMetricsResponseDTO;
import com.dmBackend.app.useCase.metrics.GetDashboardMetricsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metrics")
@RequiredArgsConstructor
public class MetricsController {

    private final GetDashboardMetricsUseCase getDashboardMetricsUseCase;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardMetricsResponseDTO> getDashboard(
            @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(getDashboardMetricsUseCase.execute(jwt.getSubject()));
    }
}
