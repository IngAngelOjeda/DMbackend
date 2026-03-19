package com.dmBackend.app.useCase.user;

import com.dmBackend.app.dto.user.UserStatusUpdateDTO;
import com.dmBackend.app.dto.user.UserUpdateResponseDTO;
import com.dmBackend.domain.port.user.UserService;
import com.dmBackend.infra.adapters.Keycloak.KeycloakUserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUserStatusUseCaseImpl implements UpdateUserStatusUseCase {

    private final KeycloakUserAdapter keycloakUserAdapter;
    private final UserService userService;

    @Override
    public UserUpdateResponseDTO execute(String id, UserStatusUpdateDTO request) {
        // Keep Keycloak and local DB in sync
        keycloakUserAdapter.updateUserStatus(id, request.getStatus());
        return userService.updateStatus(id, request);
    }
}