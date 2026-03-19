package com.dmBackend.app.useCase.user;

import com.dmBackend.app.dto.user.UserGetResponseDTO;
import com.dmBackend.domain.port.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUserByIdUseCaseImpl implements GetUserByIdUseCase {

    private final UserService userService;

    @Override
    public UserGetResponseDTO execute(String id) {
        return userService.getById(id);
    }
}