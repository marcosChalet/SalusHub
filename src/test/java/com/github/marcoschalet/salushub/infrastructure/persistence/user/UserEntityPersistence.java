package com.github.marcoschalet.salushub.infrastructure.persistence.user;

import com.github.marcoschalet.salushub.domain.user.*;
import com.github.marcoschalet.salushub.domain.user.validators.SpacesValidator;
import com.github.marcoschalet.salushub.infrastructure.security.BCryptPasswordEncoderAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    private User user;
    private UUID userId;
    private UUID companyId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        companyId = UUID.randomUUID();

        user = new User.Builder()
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
    @DisplayName("Should convert User domain to UserEntity correctly")
    void shouldConvertDomainToEntity() {
        UserEntity entity = UserEntity.fromDomain(user);

        assertNotNull(entity);
        assertEquals(userId, entity.getId());
        assertEquals("12345678909", entity.getCpfcnpj());
        assertEquals("Marcos", entity.getFirstName());
        assertEquals("Chalet", entity.getLastName());
        assertEquals("marcos@example.com", entity.getEmail());
        assertEquals(user.hashedPassword(), entity.getPasswordHash());
        assertEquals(companyId, entity.getCompanyId());
        assertEquals(Set.of(Role.ROLE_CLIENT_USER), entity.getRoles());
        assertTrue(entity.isActive());
    }

    @Test
    @DisplayName("Should convert UserEntity to User domain correctly")
    void shouldConvertEntityToDomain() {
        UserEntity entity = new UserEntity();
        entity.setId(userId);
        entity.setCpfcnpj("12345678909");
        entity.setFirstName("Marcos");
        entity.setLastName("Chalet");
        entity.setEmail("marcos@example.com");
        entity.setPasswordHash(user.hashedPassword());
        entity.setCompanyId(companyId);
        entity.setRoles(Set.of(Role.ROLE_CLIENT_USER));
        entity.setActive(true);

        User domain = entity.toDomain();

        assertNotNull(domain);
        assertEquals(userId, domain.id().orElse(null));
        assertEquals("12345678909", domain.cpfcnpj());
        assertEquals("Marcos", domain.firstName());
        assertEquals("Chalet", domain.lastName());
        assertEquals("marcos@example.com", domain.email());
        assertEquals(user.hashedPassword(), domain.hashedPassword());
        assertEquals(companyId, domain.companyId());
        assertEquals(Set.of(Role.ROLE_CLIENT_USER), domain.roles());
        assertTrue(domain.isActive());
    }

    @Test
    @DisplayName("Should preserve data when converting from domain to entity and back")
    void shouldPreserveDataInRoundTripConversion() {
        UserEntity entity = UserEntity.fromDomain(user);
        User convertedUser = entity.toDomain();

        assertNotNull(convertedUser);
        assertEquals(user.id(), convertedUser.id());
        assertEquals(user.cpfcnpj(), convertedUser.cpfcnpj());
        assertEquals(user.firstName(), convertedUser.firstName());
        assertEquals(user.lastName(), convertedUser.lastName());
        assertEquals(user.email(), convertedUser.email());
        assertEquals(user.hashedPassword(), convertedUser.hashedPassword());
        assertEquals(user.companyId(), convertedUser.companyId());
        assertEquals(user.roles(), convertedUser.roles());
        assertEquals(user.isActive(), convertedUser.isActive());
    }
}
