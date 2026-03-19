package com.dmBackend.app.useCase.session;

import com.dmBackend.app.dto.session.CloseSessionRequestDTO;
import com.dmBackend.app.dto.session.SessionResponseDTO;
import com.dmBackend.domain.port.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CloseSessionUseCaseImpl implements CloseSessionUseCase {

    private final SessionService sessionService;

    @Override
    public SessionResponseDTO execute(String userId, Long sessionId, CloseSessionRequestDTO request) {
        return sessionService.close(userId, sessionId, request);
    }
}