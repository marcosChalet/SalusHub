package com.github.marcoschalet.salushub.infrastructure.security;

import com.github.marcoschalet.salushub.domain.user.PasswordEncoder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BCryptPasswordEncoderAdapterTest {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoderAdapter();

    @Test
    void shouldEncodePassword() {
        String rawPassword = "mySecret123";
        String hashedPassword = passwordEncoder.encode(rawPassword);

        assertNotNull(hashedPassword);
        assertNotEquals(rawPassword, hashedPassword);
        assertTrue(hashedPassword.startsWith("$2"));
    }

    @Test
    void shouldMatchEncodedPassword() {
        String rawPassword = "mySecret123";
        String hashedPassword = passwordEncoder.encode(rawPassword);

        assertTrue(passwordEncoder.matches(rawPassword, hashedPassword));
    }

    @Test
    void shouldNotMatchDifferentPassword() {
        String rawPassword = "mySecret123";
        String hashedPassword = passwordEncoder.encode(rawPassword);

        assertFalse(passwordEncoder.matches("wrongPassword", hashedPassword));
    }

    @Test
    void shouldGenerateDifferentHashesForSamePassword() {
        String rawPassword = "mySecret123";
        String hash1 = passwordEncoder.encode(rawPassword);
        String hash2 = passwordEncoder.encode(rawPassword);

        assertNotEquals(hash1, hash2);
    }
}
