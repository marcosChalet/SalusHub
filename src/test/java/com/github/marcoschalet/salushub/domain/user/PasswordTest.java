package com.github.marcoschalet.salushub.domain.user;

import com.github.marcoschalet.salushub.api.user.validator.PasswordValidator;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.util.List;

class PasswordTest {

    private final SimplePasswordEncoder simplePasswordEncoder = new SimplePasswordEncoder();
    private final List<PasswordValidator> testValidators = List.of(
            new TestValidator()
    );

    @Mock
    private PasswordEncoder encoder;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreatePasswordWithCustomValidators() {
        Password password = Password.create("A123456", testValidators, simplePasswordEncoder);
        assertEquals("encoded_A123456", password.hashedPassword());
    }

    @Test
    void shouldThrowExceptionIfPasswordDoesNotMatchWithValidators() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Password.create("123456", testValidators, simplePasswordEncoder)
        );
        assertEquals("Password must be start with 'A' character", exception.getMessage());
    }

    @Test
    void shouldReturnTrueWhenPasswordMatches() {
        String rawPassword = "password";
        String hashedPassword = "hash_password";
        Password password = new Password(hashedPassword);

        when(encoder.matches(rawPassword, hashedPassword)).thenReturn(true);

        boolean result = password.matches(rawPassword, encoder);

        assertTrue(result);
        verify(encoder).matches(rawPassword, hashedPassword);
    }

    @Test
    void shouldReturnFalseWhenPasswordDoesNotMatch() {
        String wrongRawPassword = "wrongPassword";
        String hashedPassword = "hash_password"; // raw password is "password"
        Password password = new Password(hashedPassword);

        when(encoder.matches(wrongRawPassword, hashedPassword)).thenReturn(false);

        boolean result = password.matches(wrongRawPassword, encoder);

        assertFalse(result);
        verify(encoder).matches(wrongRawPassword, hashedPassword);
    }

    private static class TestValidator implements PasswordValidator {
        @Override
        public void validate(String password) {
            if (!password.startsWith("A"))
                throw new IllegalArgumentException("Password must be start with 'A' character");
        }
    }

    private static class SimplePasswordEncoder implements PasswordEncoder {
        @Override
        public String encode(String rawPassword) {
            return "encoded_" + rawPassword;
        }

        @Override
        public boolean matches(String rawPassword, String encodedPassword) {
            return encodedPassword.equals(encode(rawPassword));
        }
    }
}
