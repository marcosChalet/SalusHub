package com.github.marcoschalet.salushub.domain.user;

import com.github.marcoschalet.salushub.api.user.validator.PasswordValidator;

import java.util.List;

public record Password(String hashedPassword) {

    public static Password create(String rawPassword, List<PasswordValidator> validators, PasswordEncoder encoder) {
        for (PasswordValidator validator : validators) {
            validator.validate(rawPassword);
        }
        return new Password(encoder.encode(rawPassword));
    }

    public boolean matches(String rawPassword, PasswordEncoder encoder) {
        return encoder.matches(rawPassword, this.hashedPassword);
    }
}
