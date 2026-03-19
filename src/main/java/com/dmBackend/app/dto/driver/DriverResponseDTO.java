package com.dmBackend.app.dto.driver;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class DriverResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String documentNumber;
    private String phone;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}