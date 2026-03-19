package com.dmBackend.app.useCase.user;

import com.dmBackend.app.dto.user.UserPageResponseDTO;
import com.dmBackend.domain.port.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAllUserUseCaseImpl implements GetAllUserUseCase {

    private final UserService userService;

    @Override
    public UserPageResponseDTO execute(String search, Pageable pageable) {
        return userService.getAll(search, pageable);
    }
}