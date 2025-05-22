package com.github.marcoschalet.salushub.domain.user.validator;

import com.github.marcoschalet.salushub.api.user.validator.PasswordValidator;
import com.github.marcoschalet.salushub.domain.user.validators.SpecialCharacterValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpecialCharacterValidatorTest {
    private final PasswordValidator specialCharactersValidator = new SpecialCharacterValidator();

    @Test
    void shouldThrowExceptionWhenPasswordDoesNotContainSpecialCharacters() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> specialCharactersValidator.validate("DontHaveAStrongPassword")
        );
        assertEquals("Password must contain at least one special character", exception.getMessage());
    }

    @Test
    void shouldPassWhenPasswordContainAtLastOneSpecialCharacter() {
        assertDoesNotThrow(() -> specialCharactersValidator.validate("NowIHaveA$trongPassword"));
    }

    @Test
    void shouldPassWhenPasswordContainMultipleSpecialCharacter() {
        assertDoesNotThrow(() -> specialCharactersValidator.validate("NowIH@veABigAnd$trongPassw#ord"));
    }
}
