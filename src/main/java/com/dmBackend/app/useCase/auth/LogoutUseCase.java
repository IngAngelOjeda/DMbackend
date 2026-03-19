package com.dmBackend.app.useCase.auth;

public interface LogoutUseCase {
    void execute(String refreshToken);
}