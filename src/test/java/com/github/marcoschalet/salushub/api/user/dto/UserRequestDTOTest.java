package com.github.marcoschalet.salushub.api.user.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.marcoschalet.salushub.domain.user.Role;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserRequestDTOTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldSerializeToJson() throws JsonProcessingException {
        UserRequestDTO dto = new UserRequestDTO(
                "12345678909",
                "Marcos",
                "Chalet",
                "marcos@example.com",
                "securePassword",
                UUID.fromString("8aa8c7f2-1111-2222-3333-444455556666"),
                Set.of(Role.ROLE_ADMIN)
        );

        String json = mapper.writeValueAsString(dto);

        assertTrue(json.contains("\"cpfcnpj\":\"12345678909\""));
        assertTrue(json.contains("\"firstName\":\"Marcos\""));
        assertTrue(json.contains("\"lastName\":\"Chalet\""));
        assertTrue(json.contains("\"email\":\"marcos@example.com\""));
        assertTrue(json.contains("\"password\":\"securePassword\""));
        assertTrue(json.contains("8aa8c7f2-1111-2222-3333-444455556666"));
        assertTrue(json.contains("ROLE_ADMIN"));
    }

    @Test
    void shouldDeserializeFromJson() throws JsonProcessingException {
        String json = """
                {
                  "cpfcnpj": "12345678909",
                  "firstName": "Marcos",
                  "lastName": "Chalet",
                  "email": "marcos@example.com",
                  "password": "securePassword",
                  "companyId": "8aa8c7f2-1111-2222-3333-444455556666",
                  "roles": ["ROLE_ADMIN"]
                }
                """;

        UserRequestDTO dto = mapper.readValue(json, UserRequestDTO.class);

        assertEquals("12345678909", dto.getCpfcnpj());
        assertEquals("Marcos", dto.getFirstName());
        assertEquals("Chalet", dto.getLastName());
        assertEquals("marcos@example.com", dto.getEmail());
        assertEquals("securePassword", dto.getPassword());
        assertEquals(UUID.fromString("8aa8c7f2-1111-2222-3333-444455556666"), dto.getCompanyId());
        assertTrue(dto.getRoles().contains(Role.ROLE_ADMIN));
    }
}
