package com.dmBackend.app.useCase.session;

import com.dmBackend.app.dto.session.SessionResponseDTO;

public interface GetSessionByIdUseCase {
    SessionResponseDTO execute(String userId, Long sessionId);
}