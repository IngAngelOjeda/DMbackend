package com.dmBackend.app.useCase.session;

import com.dmBackend.app.dto.session.OpenSessionRequestDTO;
import com.dmBackend.app.dto.session.SessionResponseDTO;
import com.dmBackend.domain.port.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenSessionUseCaseImpl implements OpenSessionUseCase {

    private final SessionService sessionService;

    @Override
    public SessionResponseDTO execute(String userId, OpenSessionRequestDTO request) {
        return sessionService.open(userId, request);
    }
}