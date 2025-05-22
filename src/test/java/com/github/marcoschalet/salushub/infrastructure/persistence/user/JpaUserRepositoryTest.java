package com.github.marcoschalet.salushub.infrastructure.persistence.user;

import com.github.marcoschalet.salushub.domain.user.*;
import com.github.marcoschalet.salushub.domain.user.validators.SpacesValidator;
import com.github.marcoschalet.salushub.infrastructure.security.BCryptPasswordEncoderAdapter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(JpaUserRepository.class)
class JpaUserRepositoryTest {

    @Autowired
    private SpringDataUserRepository springDataUserRepository;

    @Autowired
    private JpaUserRepository jpaUserRepository;

    private final UUID userId = UUID.randomUUID();
    private final UUID companyId = UUID.randomUUID();

    private User createUser() {
        return new User.Builder()
                .id(userId)
                .cpfcnpj(new CpfCnpj("12345678909"))
                .name(new Name("Marcos", "Chalet"))
                .email(new Email("marcos@example.com"))
                .password(Password.create("password", List.of(new SpacesValidator()), new BCryptPasswordEncoderAdapter()))
                .companyId(companyId)
                .roles(Set.of(Role.ROLE_CLIENT_USER))
                .active(true)
                .build();
    }

    @Test
    @DisplayName("Should save and retrieve user by ID")
    void shouldSaveAndFindById() {
        User user = createUser();

        jpaUserRepository.save(user);

        Optional<User> found = jpaUserRepository.findById(userId);

        assertTrue(found.isPresent());
        assertEquals(userId, found.get().id().orElse(null));
        assertEquals("marcos@example.com", found.get().email());
    }

    @Test
    @DisplayName("Should save and retrieve user by Email")
    void shouldSaveAndFindByEmail() {
        User user = createUser();

        jpaUserRepository.save(user);

        Optional<User> found = jpaUserRepository.findByEmail("marcos@example.com");

        assertTrue(found.isPresent());
        assertEquals(userId, found.get().id().orElse(null));
        assertEquals("marcos@example.com", found.get().email());
    }

    @Test
    @DisplayName("Should return empty when user not found by ID")
    void shouldReturnEmptyWhenNotFoundById() {
        Optional<User> found = jpaUserRepository.findById(UUID.randomUUID());
        assertTrue(found.isEmpty());
    }

    @Test
    @DisplayName("Should return empty when user not found by Email")
    void shouldReturnEmptyWhenNotFoundByEmail() {
        Optional<User> found = jpaUserRepository.findByEmail("notfound@example.com");
        assertTrue(found.isEmpty());
    }
}
