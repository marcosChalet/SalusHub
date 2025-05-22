package com.github.marcoschalet.salushub.domain.user.validator;

import com.github.marcoschalet.salushub.domain.user.Email;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CpfCnpjTest {
    @Test
    void shouldAcceptValidEmail() {
        assertDoesNotThrow(() -> new Email("user@example.com"));
    }

    @Test
    void shouldRejectNullEmail() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Email(null)
        );
    }

    @Test
    void shouldRejectInvalidFormat() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Email("not-an-email")
        );
    }

    @Test
    void shouldBeEqualIfSameValue() {
        Email email1 = new Email("test@example.com");
        Email email2 = new Email("test@example.com");
        assertEquals(email1, email2);
        assertEquals(email1.hashCode(), email2.hashCode());
    }
}
