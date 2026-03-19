package com.dmBackend.app.dto.platform;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatformUpdateRequestDTO {

    private String name;
    private String description;
    private Boolean active;
}