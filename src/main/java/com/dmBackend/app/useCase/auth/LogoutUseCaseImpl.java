package com.dmBackend.app.useCase.auth;

import com.dmBackend.domain.port.user.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogoutUseCaseImpl implements LogoutUseCase {

    private final AuthService authService;

    @Override
    public void execute(String refreshToken) {
        authService.logout(refreshToken);
    }
}