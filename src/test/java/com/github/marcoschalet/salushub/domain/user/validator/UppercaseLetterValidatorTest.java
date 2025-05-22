package com.github.marcoschalet.salushub.domain.user.validator;

import com.github.marcoschalet.salushub.api.user.validator.PasswordValidator;
import com.github.marcoschalet.salushub.domain.user.validators.UppercaseLetterValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UppercaseLetterValidatorTest {
    private final PasswordValidator uppercaseValidator = new UppercaseLetterValidator();

    @Test
    void shouldThrowExceptionWhenPasswordHasNoUppercaseCharacter() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> uppercaseValidator.validate("password")
        );
        assertEquals("Password must contain at least one uppercase letter", exception.getMessage());
    }

    @Test
    void shouldPassWhenPasswordHasOneUppercaseCharacter() {
        assertDoesNotThrow(() -> uppercaseValidator.validate("correcTpassword"));
    }

    @Test
    void shouldPassWhenPasswordHasManyUppercaseCharacter() {
        assertDoesNotThrow(() -> uppercaseValidator.validate("CorrecTpaSSwoRd"));
    }
}
