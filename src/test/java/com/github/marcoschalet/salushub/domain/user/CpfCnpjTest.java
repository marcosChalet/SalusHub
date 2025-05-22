package com.github.marcoschalet.salushub.domain.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class CpfCnpjTest {

    // Valid CPFs with and without formatting
    @ParameterizedTest
    @ValueSource(strings = {
            "529.982.247-25",
            "52998224725",
            "123.456.789-09",
            "12345678909"
    })
    void shouldAcceptValidCpf(String input) {
        assertDoesNotThrow(() -> new CpfCnpj(input));
    }

    // Invalid CPFs
    @ParameterizedTest
    @ValueSource(strings = {
            "123",
            "111.111.111-11",
            "529.982.247-26",
            "123.456.789-0a",
            "000.000.000-00"
    })
    void shouldRejectInvalidCpf(String input) {
        assertThrows(IllegalArgumentException.class, () -> new CpfCnpj(input));
    }

    // Valid CNPJs with and without formatting
    @ParameterizedTest
    @ValueSource(strings = {
            "12.345.678/0001-95",
            "12345678000195",
            "88.999.222/0001-00",
            "88999222000100"
    })
    void shouldAcceptValidCnpj(String input) {
        assertDoesNotThrow(() -> new CpfCnpj(input));
    }

    // Invalid CNPJs
    @ParameterizedTest
    @ValueSource(strings = {
            "12.345.678/0001-00",
            "11.111.111/1111-11",
            "1234567800019",
            "00.000.000/0000-00"
    })
    void shouldRejectInvalidCnpj(String input) {
        assertThrows(IllegalArgumentException.class, () -> new CpfCnpj(input));
    }

    @Test
    void shouldThrowNullPointerExceptionForNullInput() {
        assertThrows(NullPointerException.class, () -> new CpfCnpj(null));
    }

    @Test
    void shouldContainOriginalValueInExceptionMessage() {
        String invalidCpf = "111.111.111-11";
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> new CpfCnpj(invalidCpf)
        );

        assertTrue(exception.getMessage().contains(invalidCpf));
    }
}