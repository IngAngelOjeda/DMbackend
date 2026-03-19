package com.dmBackend.app.useCase.auth;

import com.dmBackend.app.dto.auth.AuthResponseDTO;
import com.dmBackend.app.dto.auth.RefreshTokenRequestDTO;
import com.dmBackend.domain.port.user.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenUseCaseImpl implements RefreshTokenUseCase {

    private final AuthService authService;

    @Override
    public AuthResponseDTO execute(RefreshTokenRequestDTO request) {
        return authService.refresh(request);
    }
}