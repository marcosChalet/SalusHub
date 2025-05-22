package com.github.marcoschalet.salushub.api.user.dto;

import com.github.marcoschalet.salushub.domain.user.User;

public class UserResponseMapper {
    private UserResponseMapper() {}
    public static UserResponseDTO from(User user) {
        return new UserResponseDTO(
                user.id().orElse(null), user.firstName(), user.lastName(), user.email(), user.companyId(), user.roles()
        );
    }
}
