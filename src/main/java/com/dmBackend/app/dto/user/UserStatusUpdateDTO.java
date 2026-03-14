package com.dmBackend.app.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserStatusUpdateDTO {

    @NotNull(message = "El estado no puede estar vacío.")
    private Boolean status;
}
