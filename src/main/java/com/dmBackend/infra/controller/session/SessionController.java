package com.dmBackend.infra.controller.session;

import com.dmBackend.app.dto.session.CloseSessionRequestDTO;
import com.dmBackend.app.dto.session.OpenSessionRequestDTO;
import com.dmBackend.app.dto.session.SessionResponseDTO;
import com.dmBackend.app.useCase.session.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final OpenSessionUseCase openSessionUseCase;
    private final CloseSessionUseCase closeSessionUseCase;
    private final GetAllSessionsUseCase getAllSessionsUseCase;
    private final GetSessionByIdUseCase getSessionByIdUseCase;

    @PostMapping
    public ResponseEntity<SessionResponseDTO> open(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody @Valid OpenSessionRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(openSessionUseCase.execute(jwt.getSubject(), request));
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<SessionResponseDTO> close(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long id,
            @RequestBody @Valid CloseSessionRequestDTO request) {
        return ResponseEntity.ok(closeSessionUseCase.execute(jwt.getSubject(), id, request));
    }

    @GetMapping
    public ResponseEntity<Page<SessionResponseDTO>> getAll(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "startTime,desc") String[] sort) {
        String sortBy = sort[0];
        String sortDir = sort.length > 1 ? sort[1] : "desc";
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        return ResponseEntity.ok(getAllSessionsUseCase.execute(jwt.getSubject(), pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionResponseDTO> getById(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long id) {
        return ResponseEntity.ok(getSessionByIdUseCase.execute(jwt.getSubject(), id));
    }
}