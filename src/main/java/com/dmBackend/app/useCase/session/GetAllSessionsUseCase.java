package com.dmBackend.app.useCase.session;

import com.dmBackend.app.dto.session.SessionResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetAllSessionsUseCase {
    Page<SessionResponseDTO> execute(String userId, Pageable pageable);
}