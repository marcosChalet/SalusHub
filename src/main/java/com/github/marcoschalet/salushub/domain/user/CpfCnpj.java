package com.github.marcoschalet.salushub.domain.user;

import com.github.marcoschalet.salushub.domain.shared.Patterns;

import java.util.Objects;

public record CpfCnpj(String value) {

    public CpfCnpj {
        Objects.requireNonNull(value, "CPF/CNPJ cannot be null");
        String numeric = value.replaceAll(Patterns.ONLY_DIGITS, "");

        if (!(isValidCpf(numeric) || isValidCnpj(numeric))) {
            throw new IllegalArgumentException("Invalid CPF/CNPJ: " + value);
        }
    }

    private static boolean isValidCpf(String cpf) {
        if (cpf.length() != 11 || cpf.matches(Patterns.ALL_EQUAL_DIGITS_CPF)) return false;

        int d1 = 0;
        int d2 = 0;
        for (int i = 0; i < 9; i++) {
            int dig = cpf.charAt(i) - '0';
            d1 += dig * (10 - i);
            d2 += dig * (11 - i);
        }
        d1 = 11 - (d1 % 11); d1 = d1 >= 10 ? 0 : d1;
        d2 += d1 * 2;
        d2 = 11 - (d2 % 11); d2 = d2 >= 10 ? 0 : d2;

        return d1 == (cpf.charAt(9) - '0') && d2 == (cpf.charAt(10) - '0');
    }

    private static boolean isValidCnpj(String cnpj) {
        if (cnpj.length() != 14 || cnpj.matches(Patterns.ALL_EQUAL_DIGITS_CNPJ)) return false;

        int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int soma1 = 0;
        int soma2 = 0;
        for (int i = 0; i < 12; i++) {
            int dig = cnpj.charAt(i) - '0';
            soma1 += dig * pesos1[i];
            soma2 += dig * pesos2[i];
        }

        int d1 = soma1 % 11; d1 = d1 < 2 ? 0 : 11 - d1;
        soma2 += d1 * pesos2[12];
        int d2 = soma2 % 11; d2 = d2 < 2 ? 0 : 11 - d2;

        return d1 == (cnpj.charAt(12) - '0') && d2 == (cnpj.charAt(13) - '0');
    }
}
