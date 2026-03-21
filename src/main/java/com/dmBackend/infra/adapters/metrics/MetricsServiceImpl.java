package com.dmBackend.infra.adapters.metrics;

import com.dmBackend.app.dto.metrics.DashboardMetricsResponseDTO;
import com.dmBackend.app.dto.metrics.DriverKilometersDTO;
import com.dmBackend.app.dto.metrics.PlatformUsageDTO;
import com.dmBackend.app.dto.metrics.VehicleKilometersDTO;
import com.dmBackend.domain.model.enums.SessionStatus;
import com.dmBackend.domain.port.metrics.MetricsService;
import com.dmBackend.infra.repository.DriverRepository;
import com.dmBackend.infra.repository.SessionRepository;
import com.dmBackend.infra.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MetricsServiceImpl implements MetricsService {

    private static final int TOP_LIMIT = 5;

    private final SessionRepository sessionRepository;
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;

    @Override
    @Transactional(readOnly = true)
    public DashboardMetricsResponseDTO getDashboard(String userId) {
        Map<SessionStatus, Long> sessionCounts = buildSessionCounts(userId);

        Long totalKilometers = sessionRepository.sumKilometersByUserId(userId);

        List<VehicleKilometersDTO> topVehicles = sessionRepository
                .findTopVehiclesByKilometers(userId, PageRequest.of(0, TOP_LIMIT))
                .stream()
                .map(row -> VehicleKilometersDTO.builder()
                        .vehicleId((Long) row[0])
                        .plate((String) row[1])
                        .brand((String) row[2])
                        .model((String) row[3])
                        .kilometers((Long) row[4])
                        .build())
                .toList();

        List<DriverKilometersDTO> topDrivers = sessionRepository
                .findTopDriversByKilometers(userId, PageRequest.of(0, TOP_LIMIT))
                .stream()
                .map(row -> DriverKilometersDTO.builder()
                        .driverId((Long) row[0])
                        .fullName(row[1] + " " + row[2])
                        .kilometers((Long) row[3])
                        .build())
                .toList();

        List<PlatformUsageDTO> sessionsByPlatform = sessionRepository
                .findSessionsByPlatform(userId)
                .stream()
                .map(row -> PlatformUsageDTO.builder()
                        .platformId((Long) row[0])
                        .platformName((String) row[1])
                        .sessionCount((Long) row[2])
                        .totalKilometers((Long) row[3])
                        .build())
                .toList();

        return DashboardMetricsResponseDTO.builder()
                .totalSessions(sessionCounts.values().stream().mapToLong(Long::longValue).sum())
                .openSessions(sessionCounts.getOrDefault(SessionStatus.OPEN, 0L))
                .closedSessions(sessionCounts.getOrDefault(SessionStatus.CLOSED, 0L))
                .cancelledSessions(sessionCounts.getOrDefault(SessionStatus.CANCELLED, 0L))
                .totalKilometers(totalKilometers != null ? totalKilometers : 0L)
                .totalVehicles(vehicleRepository.countByUserId(userId))
                .totalDrivers(driverRepository.countByUserId(userId))
                .topVehiclesByKilometers(topVehicles)
                .topDriversByKilometers(topDrivers)
                .sessionsByPlatform(sessionsByPlatform)
                .build();
    }

    private Map<SessionStatus, Long> buildSessionCounts(String userId) {
        Map<SessionStatus, Long> counts = new EnumMap<>(SessionStatus.class);
        sessionRepository.countByUserIdGroupByStatus(userId)
                .forEach(row -> counts.put((SessionStatus) row[0], (Long) row[1]));
        return counts;
    }
}
