package com.dmBackend.app.useCase.session;

import com.dmBackend.app.dto.session.CloseSessionRequestDTO;
import com.dmBackend.app.dto.session.SessionResponseDTO;

public interface CloseSessionUseCase {
    SessionResponseDTO execute(String userId, Long sessionId, CloseSessionRequestDTO request);
}