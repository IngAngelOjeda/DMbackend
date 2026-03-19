package com.dmBackend.app.dto.vehicle;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class VehicleResponseDTO {

    private Long id;
    private String brand;
    private String model;
    private String plate;
    private Integer yearModel;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}