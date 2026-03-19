package com.dmBackend.app.useCase.user;

import com.dmBackend.app.dto.user.UserUpdateRequestDTO;
import com.dmBackend.app.dto.user.UserUpdateResponseDTO;
import com.dmBackend.domain.port.user.UserService;
import com.dmBackend.infra.adapters.Keycloak.KeycloakUserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    private final KeycloakUserAdapter keycloakUserAdapter;
    private final UserService userService;

    @Override
    public UserUpdateResponseDTO execute(String userId, UserUpdateRequestDTO request) {
        // Keep Keycloak and local DB in sync
        keycloakUserAdapter.updateUser(userId, request.getEmail(), request.getFirstName(), request.getLastName());
        return userService.update(userId, request);
    }
}