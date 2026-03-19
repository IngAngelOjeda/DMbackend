package com.dmBackend.app.useCase.auth;

import com.dmBackend.app.dto.auth.RegisterRequestDTO;
import com.dmBackend.app.dto.user.UserRequestDTO;
import com.dmBackend.app.dto.user.UserResponseDTO;
import com.dmBackend.domain.model.generic.RoleAPI;
import com.dmBackend.domain.port.user.UserService;
import com.dmBackend.infra.adapters.Keycloak.KeycloakUserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterUseCaseImpl implements RegisterUseCase {

    private final KeycloakUserAdapter keycloakUserAdapter;
    private final UserService userService;

    @Override
    public UserResponseDTO execute(RegisterRequestDTO request) {
        // Build the internal request forcing role USER — caller cannot override it
        UserRequestDTO internalRequest = new UserRequestDTO(
                request.getUsername(),
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                request.getDocumentNumber(),
                request.getPassword(),
                RoleAPI.USER
        );

        String keycloakId = keycloakUserAdapter.createUser(internalRequest);
        return userService.create(internalRequest, keycloakId);
    }
}