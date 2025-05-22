package com.github.marcoschalet.salushub.domain.user.validators;

import com.github.marcoschalet.salushub.api.user.validator.PasswordValidator;
import org.springframework.stereotype.Component;

@Component
public class MinLengthValidator implements PasswordValidator {
    @Override
    public void validate(String password) {
        int minLength = 10;
        if (password == null || password.length() < minLength) {
            throw new IllegalArgumentException("Password must be at last " + minLength + " characters.");
        }
    }
}
