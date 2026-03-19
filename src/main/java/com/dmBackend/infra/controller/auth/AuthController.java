package com.dmBackend.infra.controller.auth;

import com.dmBackend.app.dto.auth.AuthRequestDTO;
import com.dmBackend.app.dto.auth.AuthResponseDTO;
import com.dmBackend.app.dto.auth.LogoutRequestDTO;
import com.dmBackend.app.dto.auth.RefreshTokenRequestDTO;
import com.dmBackend.app.dto.auth.RegisterRequestDTO;
import com.dmBackend.app.dto.user.UserResponseDTO;
import com.dmBackend.app.useCase.auth.LoginUseCase;
import com.dmBackend.app.useCase.auth.LogoutUseCase;
import com.dmBackend.app.useCase.auth.RefreshTokenUseCase;
import com.dmBackend.app.useCase.auth.RegisterUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final LogoutUseCase logoutUseCase;
    private final RegisterUseCase registerUseCase;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthRequestDTO request) {
        return ResponseEntity.ok(loginUseCase.execute(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(@RequestBody @Valid RefreshTokenRequestDTO request) {
        return ResponseEntity.ok(refreshTokenUseCase.execute(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody @Valid LogoutRequestDTO request) {
        logoutUseCase.execute(request.getRefreshToken());
        return ResponseEntity.noContent().build();
    }

    /**
     * Public endpoint — role is always set to USER, caller cannot override it.
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid RegisterRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registerUseCase.execute(request));
    }
}