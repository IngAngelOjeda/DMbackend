package com.dmBackend.app.dto.user;

import com.dmBackend.domain.model.generic.RoleAPI;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserUpdateRequestDTO {

    @Email(message = "El email debe ser válido")
    private String email;

    @NotBlank(message = "El nombre es obligatorio")
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio")
    private String lastName;

    @NotNull(message = "El rol es obligatorio")
    private RoleAPI role;
}
