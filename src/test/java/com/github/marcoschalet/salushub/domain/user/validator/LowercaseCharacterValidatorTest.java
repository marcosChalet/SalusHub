package com.github.marcoschalet.salushub.domain.user.validator;

import com.github.marcoschalet.salushub.api.user.validator.PasswordValidator;
import com.github.marcoschalet.salushub.domain.user.validators.LowercaseCharacterValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LowercaseCharacterValidatorTest {
    private final PasswordValidator lowercaseCharacterValidator = new LowercaseCharacterValidator();

    @Test
    void shouldThrowExceptionWhenPasswordHasNoLowercaseCharacter() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> lowercaseCharacterValidator.validate("PASSWORD")
        );
        assertEquals("Password must contain at least one lowercase letter", exception.getMessage());
    }

    @Test
    void shouldPassWhenPasswordContainsAtLastOneLowercaseCharacters() {
        assertDoesNotThrow(() -> lowercaseCharacterValidator.validate("PAsSWORD"));
    }

    @Test
    void shouldPassWhenPasswordContainsMultipleLowercaseCharacters() {
        assertDoesNotThrow(() -> lowercaseCharacterValidator.validate("PaSsWoRd"));
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsEmpty() {
        assertThrows(
                IllegalArgumentException.class,
                () -> lowercaseCharacterValidator.validate("")
        );
    }
}
