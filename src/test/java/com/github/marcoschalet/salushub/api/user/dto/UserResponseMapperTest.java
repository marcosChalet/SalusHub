package com.github.marcoschalet.salushub.api.user.dto;

import com.github.marcoschalet.salushub.domain.user.*;
import com.github.marcoschalet.salushub.domain.user.validators.SpacesValidator;
import com.github.marcoschalet.salushub.infrastructure.security.BCryptPasswordEncoderAdapter;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserResponseMapperTest {

    @Test
    void shouldMapUserToUserResponseDTO() {
        UUID userId = UUID.fromString("7a7a7a7a-aaaa-bbbb-cccc-111111111111");
        UUID companyId = UUID.fromString("8aa8c7f2-1111-2222-3333-444455556666");

        User user = new User.Builder()
                .id(userId)
                .cpfcnpj(new CpfCnpj("12345678909"))
                .name(new Name("Marcos", "Chalet"))
                .email(new Email("marcos@example.com"))
                .password(Password.create("password", List.of(new SpacesValidator()), new BCryptPasswordEncoderAdapter()))
                .companyId(companyId)
                .roles(Set.of(Role.ROLE_ADMIN, Role.ROLE_API_CONSUMER))
                .build();

        UserResponseDTO dto = UserResponseMapper.from(user);

        assertNotNull(dto);
        assertEquals(userId, dto.getId());
        assertEquals("Marcos", dto.getFirstName());
        assertEquals("Chalet", dto.getLastName());
        assertEquals("marcos@example.com", dto.getEmail());
        assertEquals(companyId, dto.getCompanyId());
        assertTrue(dto.getRoles().contains(Role.ROLE_ADMIN));
        assertTrue(dto.getRoles().contains(Role.ROLE_API_CONSUMER));
    }
}
