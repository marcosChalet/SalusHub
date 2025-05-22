package com.github.marcoschalet.salushub.infrastructure.persistence.user;

import com.github.marcoschalet.salushub.domain.user.*;
import com.github.marcoschalet.salushub.infrastructure.security.BCryptPasswordEncoderAdapter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityMapperTest {

    private final UUID userId = UUID.randomUUID();
    private final UUID companyId = UUID.randomUUID();

    private User createUser() {
        return new User.Builder()
                .id(userId)
                .cpfcnpj(new CpfCnpj("12345678909"))
                .name(new Name("Marcos", "Chalet"))
                .email(new Email("marcos@example.com"))
                .password(Password.create("password", List.of(), new BCryptPasswordEncoderAdapter()))
                .companyId(companyId)
                .roles(Set.of(Role.ROLE_CLIENT_USER))
                .active(true)
                .build();
    }

    private UserEntity createUserEntity() {
        UserEntity entity = new UserEntity();
        entity.setId(userId);
        entity.setCpfcnpj("12345678909");
        entity.setFirstName("Marcos");
        entity.setLastName("Chalet");
        entity.setEmail("marcos@example.com");
        entity.setPasswordHash("hashedPassword");
        entity.setActive(true);
        entity.setCompanyId(companyId);
        entity.setRoles(Set.of(Role.ROLE_CLIENT_USER));
        return entity;
    }

    @Test
    @DisplayName("Should map User domain to UserEntity")
    void shouldMapToEntity() {
        User user = createUser();

        UserEntity entity = UserEntityMapper.toEntity(user);

        assertNotNull(entity);
        assertEquals(userId, entity.getId());
        assertEquals("12345678909", entity.getCpfcnpj());
        assertEquals("Marcos", entity.getFirstName());
        assertEquals("Chalet", entity.getLastName());
        assertEquals("marcos@example.com", entity.getEmail());
        assertEquals(user.hashedPassword(), entity.getPasswordHash());
        assertTrue(entity.isActive());
        assertEquals(companyId, entity.getCompanyId());
        assertEquals(Set.of(Role.ROLE_CLIENT_USER), entity.getRoles());
    }

    @Test
    @DisplayName("Should map UserEntity to User domain")
    void shouldMapToDomain() {
        UserEntity entity = createUserEntity();

        User user = UserEntityMapper.toDomain(entity);

        assertNotNull(user);
        assertEquals(userId, user.id().orElse(null));
        assertEquals("12345678909", user.cpfcnpj());
        assertEquals("Marcos", user.firstName());
        assertEquals("Chalet", user.lastName());
        assertEquals("marcos@example.com", user.email());
        assertEquals("hashedPassword", user.hashedPassword());
        assertTrue(user.isActive());
        assertEquals(companyId, user.companyId());
        assertEquals(Set.of(Role.ROLE_CLIENT_USER), user.roles());
    }
}
