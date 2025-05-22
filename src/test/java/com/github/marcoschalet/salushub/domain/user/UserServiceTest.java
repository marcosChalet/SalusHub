package com.github.marcoschalet.salushub.domain.user;

import com.github.marcoschalet.salushub.api.user.dto.UserRequestDTO;
import com.github.marcoschalet.salushub.api.user.validator.PasswordValidator;
import com.github.marcoschalet.salushub.application.user.UserService;
import com.github.marcoschalet.salushub.infrastructure.persistence.user.JpaUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JpaUserRepository userRepository;

    @Mock
    private PasswordValidator passwordValidator;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(passwordEncoder, userRepository, List.of(passwordValidator));
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        // Given
        UUID companyId = UUID.randomUUID();

        UserRequestDTO dto = new UserRequestDTO();
        dto.setFirstName("Marcos");
        dto.setLastName("Chalet");
        dto.setCpfcnpj("12345678909");
        dto.setEmail("marcos@example.com");
        dto.setPassword("123456");
        dto.setCompanyId(companyId);
        dto.setRoles(Set.of(Role.ROLE_ADMIN));

        when(passwordEncoder.encode("123456")).thenReturn("hashed");
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        // When
        User user = userService.registerUser(dto);

        // Then
        assertEquals("Marcos", user.firstName());
        assertEquals("marcos@example.com", user.email());
        assertEquals("12345678909", user.cpfcnpj());
        verify(passwordEncoder).encode("123456");
        assertEquals(companyId, user.companyId());
        assertEquals(user.roles(), Set.of(Role.ROLE_ADMIN));
        assertTrue(user.isActive());
    }

    @Test
    void shouldFindUserById() {
        UUID id = UUID.randomUUID();
        User user = mock(User.class);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User result = userService.findById(id);

        assertEquals(user, result);
    }

    @Test
    void shouldFindUserByEmail() {
        String emailValue = "marcos@example.com";
        Email email = new Email(emailValue);
        User user = mock(User.class);
        when(userRepository.findByEmail(emailValue)).thenReturn(Optional.of(user));

        User result = userService.findByEmail(email);

        assertEquals(user, result);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundById() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findById(id));
    }

    @Test
    void shouldChangeUserPassword() {
        User user = mock(User.class);
        when(passwordEncoder.encode("newPassword")).thenReturn("newPasswordHashed");

        userService.changeUserPassword(user, "newPassword");

        verify(user).changePassword(any(Password.class));
    }
}
