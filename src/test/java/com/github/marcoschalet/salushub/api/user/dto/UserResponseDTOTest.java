package com.github.marcoschalet.salushub.api.user.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.marcoschalet.salushub.domain.user.Role;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserResponseDTOTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldSerializeToJson() throws JsonProcessingException {
        UserResponseDTO dto = new UserResponseDTO(
                UUID.fromString("7a7a7a7a-aaaa-bbbb-cccc-111111111111"),
                "Marcos",
                "Chalet",
                "marcos@example.com",
                UUID.fromString("8aa8c7f2-1111-2222-3333-444455556666"),
                Set.of(Role.ROLE_ADMIN, Role.ROLE_API_CONSUMER)
        );

        String json = mapper.writeValueAsString(dto);

        assertTrue(json.contains("\"id\":\"7a7a7a7a-aaaa-bbbb-cccc-111111111111\""));
        assertTrue(json.contains("\"firstName\":\"Marcos\""));
        assertTrue(json.contains("\"lastName\":\"Chalet\""));
        assertTrue(json.contains("\"email\":\"marcos@example.com\""));
        assertTrue(json.contains("\"companyId\":\"8aa8c7f2-1111-2222-3333-444455556666\""));
        assertTrue(json.contains("ROLE_ADMIN"));
        assertTrue(json.contains("ROLE_API_CONSUMER"));
    }

    @Test
    void shouldDeserializeFromJson() throws JsonProcessingException {
        String json = """
                {
                  "id": "7a7a7a7a-aaaa-bbbb-cccc-111111111111",
                  "firstName": "Marcos",
                  "lastName": "Chalet",
                  "email": "marcos@example.com",
                  "companyId": "8aa8c7f2-1111-2222-3333-444455556666",
                  "roles": ["ROLE_ADMIN", "ROLE_API_CONSUMER"]
                }
                """;

        UserResponseDTO dto = mapper.readValue(json, UserResponseDTO.class);

        assertEquals(UUID.fromString("7a7a7a7a-aaaa-bbbb-cccc-111111111111"), dto.getId());
        assertEquals("Marcos", dto.getFirstName());
        assertEquals("Chalet", dto.getLastName());
        assertEquals("marcos@example.com", dto.getEmail());
        assertEquals(UUID.fromString("8aa8c7f2-1111-2222-3333-444455556666"), dto.getCompanyId());
        assertTrue(dto.getRoles().contains(Role.ROLE_ADMIN));
        assertTrue(dto.getRoles().contains(Role.ROLE_API_CONSUMER));
    }
}
