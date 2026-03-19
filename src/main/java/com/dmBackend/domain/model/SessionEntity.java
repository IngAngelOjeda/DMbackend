package com.dmBackend.domain.model;

import com.dmBackend.domain.model.enums.SessionStatus;
import com.dmBackend.domain.model.generic.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sessions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionEntity extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private DriverEntity driver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private VehicleEntity vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "platform_id", nullable = false)
    private PlatformEntity platform;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "initial_mileage", nullable = false)
    private Integer initialMileage;

    @Column(name = "final_mileage")
    private Integer finalMileage;

    /**
     * Fuel level as percentage (0-100).
     */
    @Column(name = "initial_fuel_level")
    private Integer initialFuelLevel;

    @Column(name = "final_fuel_level")
    private Integer finalFuelLevel;

    @Column(length = 500)
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private SessionStatus status = SessionStatus.OPEN;
}