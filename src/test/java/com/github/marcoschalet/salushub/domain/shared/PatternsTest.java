package com.github.marcoschalet.salushub.domain.shared;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class PatternsTest {

    // Tests for ONLY_DIGITS pattern (which actually matches NON-digit characters)
    @ParameterizedTest
    @ValueSource(strings = {"abc", "a1b2c3", "@#$", " ", "\n"})
    void onlyDigitsPattern_shouldMatchNonDigitCharacters(String input) {
        assertTrue(input.matches(".*" + Patterns.ONLY_DIGITS + ".*"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "456789", "0", ""})
    void onlyDigitsPattern_shouldNotMatchPureDigitStrings(String input) {
        assertFalse(input.matches(".*" + Patterns.ONLY_DIGITS + ".*"));
    }

    // Tests for ALL_EQUAL_DIGITS patterns
    @ParameterizedTest
    @ValueSource(strings = {"11111111111", "22222222222", "99999999999"})
    void allEqualDigitsCpfPattern_shouldMatchEqualDigitCpfs(String input) {
        assertTrue(input.matches(Patterns.ALL_EQUAL_DIGITS_CPF));
    }

    @ParameterizedTest
    @ValueSource(strings = {"11111111112", "12345678909", "1", "123"})
    void allEqualDigitsCpfPattern_shouldNotMatchUnequalDigitCpfs(String input) {
        assertFalse(input.matches(Patterns.ALL_EQUAL_DIGITS_CPF));
    }

    @ParameterizedTest
    @ValueSource(strings = {"11111111111111", "22222222222222", "99999999999999"})
    void allEqualDigitsCnpjPattern_shouldMatchEqualDigitCnpjs(String input) {
        assertTrue(input.matches(Patterns.ALL_EQUAL_DIGITS_CNPJ));
    }

    @ParameterizedTest
    @ValueSource(strings = {"11111111111112", "12345678901234", "1", "123"})
    void allEqualDigitsCnpjPattern_shouldNotMatchUnequalDigitCnpjs(String input) {
        assertFalse(input.matches(Patterns.ALL_EQUAL_DIGITS_CNPJ));
    }

    // Tests for EMAIL pattern
    @ParameterizedTest
    @ValueSource(strings = {
            "user@example.com",
            "firstname.lastname@domain.co.uk",
            "email+tag@domain.org",
            "user@sub.domain.com",
    })
    void emailPattern_shouldMatchValidEmailFormats(String email) {
        assertTrue(email.matches(Patterns.EMAIL));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "invalid",
            "user@.com",
            "@domain.com",
            "user@domain.",
            "user@domain..com",
            "user@.com.",
            "user@-domain.com",
            "user@domain_.com"
    })
    void emailPattern_shouldNotMatchInvalidEmailFormats(String email) {
        assertFalse(email.matches(Patterns.EMAIL));
    }

    // Tests for character class patterns
    @ParameterizedTest
    @ValueSource(strings = {"Password123", "123", "1aB#", "0"})
    void containsDigitsPattern_shouldMatchStringsWithDigits(String input) {
        assertTrue(input.matches(Patterns.CONTAINS_DIGITS));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Password", "ABC", "@#$%", "", " "})
    void containsDigitsPattern_shouldNotMatchStringsWithoutDigits(String input) {
        assertFalse(input.matches(Patterns.CONTAINS_DIGITS));
    }

    @ParameterizedTest
    @ValueSource(strings = {"password", "a1B2c3", "abc", "z"})
    void containsLowercasePattern_shouldMatchStringsWithLowercase(String input) {
        assertTrue(input.matches(Patterns.CONTAINS_LOWERCASE_CHARACTERS));
    }

    @ParameterizedTest
    @ValueSource(strings = {"PASSWORD", "123", "!@#$", "", " "})
    void containsLowercasePattern_shouldNotMatchStringsWithoutLowercase(String input) {
        assertFalse(input.matches(Patterns.CONTAINS_LOWERCASE_CHARACTERS));
    }

    @ParameterizedTest
    @ValueSource(strings = {"PASSWORD", "A1b2C3", "ABC", "Z"})
    void containsUppercasePattern_shouldMatchStringsWithUppercase(String input) {
        assertTrue(input.matches(Patterns.CONTAINS_UPPERCASE_CHARACTERS));
    }

    @ParameterizedTest
    @ValueSource(strings = {"password", "123", "!@#$", "", " "})
    void containsUppercasePattern_shouldNotMatchStringsWithoutUppercase(String input) {
        assertFalse(input.matches(Patterns.CONTAINS_UPPERCASE_CHARACTERS));
    }

    @ParameterizedTest
    @ValueSource(strings = {"p@ssword", "A!B#C$", "!@#$", "@"})
    void containsSpecialCharsPattern_shouldMatchStringsWithSpecialChars(String input) {
        assertTrue(input.matches(Patterns.CONTAINS_SPECIAL_CHARACTERS));
    }

    @ParameterizedTest
    @ValueSource(strings = {"password", "ABC123", "", " ", "abcXYZ123"})
    void containsSpecialCharsPattern_shouldNotMatchStringsWithoutSpecialChars(String input) {
        assertFalse(input.matches(Patterns.CONTAINS_SPECIAL_CHARACTERS));
    }
}