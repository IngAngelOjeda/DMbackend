package com.dmBackend.app.useCase.session;

import com.dmBackend.app.dto.session.OpenSessionRequestDTO;
import com.dmBackend.app.dto.session.SessionResponseDTO;

public interface OpenSessionUseCase {
    SessionResponseDTO execute(String userId, OpenSessionRequestDTO request);
}