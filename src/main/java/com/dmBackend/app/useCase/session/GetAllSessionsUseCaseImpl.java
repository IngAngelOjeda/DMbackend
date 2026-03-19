package com.dmBackend.app.useCase.session;

import com.dmBackend.app.dto.session.SessionResponseDTO;
import com.dmBackend.domain.port.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAllSessionsUseCaseImpl implements GetAllSessionsUseCase {

    private final SessionService sessionService;

    @Override
    public Page<SessionResponseDTO> execute(String userId, Pageable pageable) {
        return sessionService.getAll(userId, pageable);
    }
}