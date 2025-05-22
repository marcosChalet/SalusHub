package com.github.marcoschalet.salushub.domain.user;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findById(UUID id);
    User save(User user);
    Optional<User> findByEmail(String email);
}
