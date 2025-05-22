package com.github.marcoschalet.salushub.domain.user.validators;

import com.github.marcoschalet.salushub.api.user.validator.PasswordValidator;
import org.springframework.stereotype.Component;

@Component
public class SpacesValidator implements PasswordValidator {
    @Override
    public void validate(String password) {
        if (password.contains(" ")) {
            throw new IllegalArgumentException("Password cannot contain spaces");
        }
    }
}
