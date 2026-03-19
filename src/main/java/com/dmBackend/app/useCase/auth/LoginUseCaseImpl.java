package com.dmBackend.app.useCase.auth;

import com.dmBackend.app.dto.auth.AuthRequestDTO;
import com.dmBackend.app.dto.auth.AuthResponseDTO;
import com.dmBackend.domain.port.user.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {

    private final AuthService authService;

    @Override
    public AuthResponseDTO execute(AuthRequestDTO request) {
        return authService.login(request);
    }
}