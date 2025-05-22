package com.github.marcoschalet.salushub.domain.user.validator;

import com.github.marcoschalet.salushub.api.user.validator.PasswordValidator;
import com.github.marcoschalet.salushub.domain.user.validators.MinLengthValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinLengthValidatorTest {
    private final PasswordValidator minLengthValidator = new MinLengthValidator();

    @Test
    void shouldThrowExceptionWhenPasswordHasNoMinLengthRequired() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> minLengthValidator.validate("PASSWORD")
        );
        assertEquals("Password must be at last 10 characters.", exception.getMessage());
    }

    @Test
    void shouldPassWhenPasswordContainsMinLengthRequired() {
        assertDoesNotThrow(() -> minLengthValidator.validate("PASSWORD10"));
    }

    @Test
    void shouldPassWhenPasswordContainsMaxThenMinLengthRequired() {
        assertDoesNotThrow(() -> minLengthValidator.validate("BIGPASSWORDPASS"));
    }
}
