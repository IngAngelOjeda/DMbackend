package com.dmBackend.app.useCase.user;

import com.dmBackend.app.dto.user.UserRequestDTO;
import com.dmBackend.app.dto.user.UserResponseDTO;
import com.dmBackend.domain.port.user.UserService;
import com.dmBackend.infra.adapters.Keycloak.KeycloakUserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final KeycloakUserAdapter keycloakUserAdapter;
    private final UserService userService;

    @Override
    public UserResponseDTO execute(UserRequestDTO request) {
        // 1. Create in Keycloak — returns the Keycloak user ID
        String keycloakId = keycloakUserAdapter.createUser(request);
        // 2. Persist locally with the Keycloak ID
        return userService.create(request, keycloakId);
    }
}