package com.github.marcoschalet.salushub.domain.user.validator;

import com.github.marcoschalet.salushub.api.user.validator.PasswordValidator;
import com.github.marcoschalet.salushub.domain.user.validators.ContainNumberValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContainNumberValidatorTest {
    private final PasswordValidator containNumberValidator = new ContainNumberValidator();

    @Test
    void shouldThrowExceptionWhenPasswordHasNoNumber() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> containNumberValidator.validate("passwordWithoutANumber")
        );
        assertEquals("Password must contain at least one number", exception.getMessage());
    }

    @Test
    void shouldPassWhenPasswordContainsAtLastOneNumber() {
        assertDoesNotThrow(() -> containNumberValidator.validate("p1sswordWithNumbers"));
    }

    @Test
    void shouldPassWhenPasswordContainsMultipleNumbers() {
        assertDoesNotThrow(() -> containNumberValidator.validate("p1ssw0rdW1thNumb3rs"));
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsEmpty() {
        assertThrows(
                IllegalArgumentException.class,
                () -> containNumberValidator.validate("")
        );
    }
}
