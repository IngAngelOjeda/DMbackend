package com.dmBackend.app.dto.platform;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatformRequestDTO {

    @NotBlank(message = "Platform name is required")
    private String name;

    private String description;
}