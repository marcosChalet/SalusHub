package com.github.marcoschalet.salushub.domain.user;

public interface PasswordEncoder {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String hashedPassword);
}
