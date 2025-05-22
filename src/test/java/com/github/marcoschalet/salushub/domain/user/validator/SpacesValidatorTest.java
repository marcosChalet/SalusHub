package com.github.marcoschalet.salushub.domain.user.validator;

import com.github.marcoschalet.salushub.api.user.validator.PasswordValidator;
import com.github.marcoschalet.salushub.domain.user.validators.SpacesValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpacesValidatorTest {
    private final PasswordValidator spacesValidator = new SpacesValidator();

    @Test
    void shouldThrowExceptionWhenPasswordHasSpaces() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> spacesValidator.validate("MY PASSWORD")
        );
        assertEquals("Password cannot contain spaces", exception.getMessage());
    }

    @Test
    void shouldPassWhenPasswordNoHasWhiteSpaces() {
        assertDoesNotThrow(() -> spacesValidator.validate("MYPASSWORD"));
    }
}
