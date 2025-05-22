package com.github.marcoschalet.salushub.domain.user.validators;

import com.github.marcoschalet.salushub.api.user.validator.PasswordValidator;
import com.github.marcoschalet.salushub.domain.shared.Patterns;
import org.springframework.stereotype.Component;

@Component
public class SpecialCharacterValidator implements PasswordValidator {

    @Override
    public void validate(String password) {
        if (!password.matches(Patterns.CONTAINS_SPECIAL_CHARACTERS)) {
            throw new IllegalArgumentException("Password must contain at least one special character");
        }
    }
}
