package com.github.marcoschalet.salushub.domain.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NameTest {

    @Test
    void shouldThrowExceptionIfDoesNotHasName() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Name("", "Chalet")
        );
        assertEquals("First name cannot be null or blank", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionIfDoesNotHasLastName() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Name("Marcos", "")
        );
        assertEquals("Last name cannot be null or blank", exception.getMessage());
    }

    @Test
    void shouldCreateName() {
        Name name = new Name("Marcos", "Chalet");
        assertEquals("Marcos Chalet", name.fullName());
        assertEquals("Marcos", name.firstName());
        assertEquals("Chalet", name.lastName());
    }

    @Test
    void shouldUpdateFirstName() {
        Name name = new Name("Marcos", "Chalet");
        name = name.withFirstName("Marcelo");
        assertEquals("Marcelo", name.firstName());
    }

    @Test
    void shouldUpdateLastName() {
        Name name = new Name("Marcos", "Chalet");
        name = name.withLastName("Silva");
        assertEquals("Silva", name.lastName());
    }
}
