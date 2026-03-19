package com.dmBackend.domain.port.session;

import com.dmBackend.app.dto.session.CloseSessionRequestDTO;
import com.dmBackend.app.dto.session.OpenSessionRequestDTO;
import com.dmBackend.app.dto.session.SessionResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SessionService {

    SessionResponseDTO open(String userId, OpenSessionRequestDTO request);

    SessionResponseDTO close(String userId, Long sessionId, CloseSessionRequestDTO request);

    Page<SessionResponseDTO> getAll(String userId, Pageable pageable);

    SessionResponseDTO getById(String userId, Long sessionId);
}