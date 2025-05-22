package com.github.marcoschalet.salushub.domain.shared;

public final class Patterns {
    public static final String ONLY_DIGITS = "\\D";
    public static final String ALL_EQUAL_DIGITS_CPF = "(\\d)\\1{10}";
    public static final String ALL_EQUAL_DIGITS_CNPJ = "(\\d)\\1{13}";
    public static final String EMAIL = "^[A-Za-z0-9+_-]+(?:\\.[A-Za-z0-9+_-]+)*@([A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z]{2,}$";
    public static final String CONTAINS_DIGITS = ".*\\d.*";
    public static final String CONTAINS_LOWERCASE_CHARACTERS = ".*[a-z].*";
    public static final String CONTAINS_UPPERCASE_CHARACTERS = ".*[A-Z].*";
    public static final String CONTAINS_SPECIAL_CHARACTERS = ".*[!@#$%^&*()_+=<>?{}\\[\\]~-].*";
    private Patterns() {}
}
