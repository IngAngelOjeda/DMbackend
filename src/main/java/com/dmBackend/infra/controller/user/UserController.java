package com.dmBackend.infra.controller.user;

import com.dmBackend.app.dto.user.UserGetResponseDTO;
import com.dmBackend.app.dto.user.UserPageResponseDTO;
import com.dmBackend.app.dto.user.UserRequestDTO;
import com.dmBackend.app.dto.user.UserResponseDTO;
import com.dmBackend.app.dto.user.UserStatusUpdateDTO;
import com.dmBackend.app.dto.user.UserUpdateRequestDTO;
import com.dmBackend.app.dto.user.UserUpdateResponseDTO;
import com.dmBackend.app.useCase.user.CreateUserUseCase;
import com.dmBackend.app.useCase.user.GetAllUserUseCase;
import com.dmBackend.app.useCase.user.GetUserByIdUseCase;
import com.dmBackend.app.useCase.user.UpdateUserStatusUseCase;
import com.dmBackend.app.useCase.user.UpdateUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final GetAllUserUseCase getAllUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final UpdateUserStatusUseCase updateUserStatusUseCase;

    @GetMapping("/getAll")
//    @PreAuthorize("hasRole('user') or hasRole('admin')")
    public UserPageResponseDTO getAllCustomers(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "firstName,asc") String[] sort
    ) {
        String sortBy = sort[0];
        String sortDir = sort.length > 1 ? sort[1] : "asc";

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        return getAllUserUseCase.execute(search, pageable);
    }

    @PostMapping("/create")
    //    @PreAuthorize("hasRole('user') or hasRole('admin')")
    public UserResponseDTO createUser(@RequestBody @Valid UserRequestDTO userRequestDTO){
        return createUserUseCase.execute(userRequestDTO);
    }

    @PutMapping("/update/{userId}")
    //    @PreAuthorize("hasRole('admin') or hasRole('user')")
    public UserUpdateResponseDTO updateUser(@PathVariable String userId, @RequestBody @Valid UserUpdateRequestDTO userUpdateRequestDTO) {
        return updateUserUseCase.execute(userId, userUpdateRequestDTO);
    }

    @GetMapping("/get/{id}")
    //    @PreAuthorize("hasRole('admin') or hasRole('user')")
    public UserGetResponseDTO getUserById(@PathVariable String id) {
        return getUserByIdUseCase.execute(id);
    }

    @PutMapping("/updateStatus/{id}")
    public UserUpdateResponseDTO updateUserStatus(
            @PathVariable String id,
            @RequestBody @Valid UserStatusUpdateDTO request
    ) {
        return updateUserStatusUseCase.execute(id, request);
    }
}
