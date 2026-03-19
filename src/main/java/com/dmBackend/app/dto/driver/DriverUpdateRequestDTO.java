package com.dmBackend.app.dto.driver;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverUpdateRequestDTO {

    private String firstName;
    private String lastName;
    private String documentNumber;
    private String phone;
    private Boolean active;
}