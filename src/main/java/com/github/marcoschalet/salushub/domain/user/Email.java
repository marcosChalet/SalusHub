package com.github.marcoschalet.salushub.domain.user;

import com.github.marcoschalet.salushub.domain.shared.Patterns;

import java.util.regex.Pattern;

public record Email(String value) {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            Patterns.EMAIL
    );

    public Email {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid email");
        }
    }
}
