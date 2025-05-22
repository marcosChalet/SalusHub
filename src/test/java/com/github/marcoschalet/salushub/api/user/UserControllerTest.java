package com.github.marcoschalet.salushub.api.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.marcoschalet.salushub.api.user.dto.UserRequestDTO;
import com.github.marcoschalet.salushub.application.user.UserService;
import com.github.marcoschalet.salushub.domain.user.*;
import com.github.marcoschalet.salushub.domain.user.validators.SpacesValidator;
import com.github.marcoschalet.salushub.infrastructure.security.BCryptPasswordEncoderAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID userId;
    private UUID companyId;
    private User user;

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
                .build();

        Mockito.reset(userService);
    }

    @Test
    @DisplayName("POST /v1/users/create - Should create user successfully")
    void shouldCreateUserSuccessfully() throws Exception {
        UserRequestDTO request = new UserRequestDTO(
                "12345678909",
                "Marcos",
                "Chalet",
                "marcos@example.com",
                "password",
                companyId,
                Set.of(Role.ROLE_CLIENT_USER)
        );

        when(userService.registerUser(any(UserRequestDTO.class))).thenReturn(user);

        mockMvc.perform(post("/v1/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.firstName").value("Marcos"))
                .andExpect(jsonPath("$.lastName").value("Chalet"))
                .andExpect(jsonPath("$.email").value("marcos@example.com"))
                .andExpect(jsonPath("$.companyId").value(companyId.toString()))
                .andExpect(jsonPath("$.roles[0]").value("ROLE_CLIENT_USER"));
    }

    @Test
    @DisplayName("GET /v1/users/{id} - Should return user by id")
    void shouldGetUserById() throws Exception {
        when(userService.findById(userId)).thenReturn(user);

        mockMvc.perform(get("/v1/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.firstName").value("Marcos"))
                .andExpect(jsonPath("$.lastName").value("Chalet"))
                .andExpect(jsonPath("$.email").value("marcos@example.com"))
                .andExpect(jsonPath("$.companyId").value(companyId.toString()))
                .andExpect(jsonPath("$.roles[0]").value("ROLE_CLIENT_USER"));
    }

    @TestConfiguration
    static class UserServiceTestConfig {
        @Bean
        public UserService userService() {
            return Mockito.mock(UserService.class);
        }
    }
}
