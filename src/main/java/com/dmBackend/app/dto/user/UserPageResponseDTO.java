package com.dmBackend.app.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserPageResponseDTO {
    private List<UserGetResponseDTO> users;
    private int page;
    private int totalPages;
    private long totalElements;
}
