package com.dmBackend.app.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String username;

    @NotBlank(message = "El password es obligatorio")
    private String password;
}
