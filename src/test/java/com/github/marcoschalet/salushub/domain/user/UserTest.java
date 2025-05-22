package com.github.marcoschalet.salushub.domain.user;

import com.github.marcoschalet.salushub.api.user.validator.PasswordValidator;
import com.github.marcoschalet.salushub.domain.user.validators.*;
import com.github.marcoschalet.salushub.infrastructure.security.BCryptPasswordEncoderAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

class UserTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PasswordValidator passwordValidator;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateUserWithValidData() {

        when(passwordEncoder.encode("myPassword")).thenReturn("hashed-myPassword");

        Password password = Password.create("myPassword", List.of(passwordValidator), passwordEncoder);
        UUID companyId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        User user = new User.Builder()
                .id(userId)
                .cpfcnpj(new CpfCnpj("12345678909"))
                .name(new Name("Marcos", "Chalet"))
                .email(new Email("marcos@example.com"))
                .password(password)
                .companyId(companyId)
                .roles(Set.of(Role.ROLE_ADMIN))
                .active(true)
                .build();

        assertEquals(userId, user.id().orElse(null));
        assertEquals("Marcos", user.firstName());
        assertEquals("Chalet", user.lastName());
        assertEquals("marcos@example.com", user.email());
        assertEquals(password.hashedPassword(), user.hashedPassword());
        assertEquals(companyId, user.companyId());
        assertEquals(user.roles(), Set.of(Role.ROLE_ADMIN));
        assertTrue(user.isActive());
    }

    private static User.Builder validUserBuilder() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoderAdapter();
        List<PasswordValidator> passwordValidators = List.of(
                new UppercaseLetterValidator(),
                new LowercaseCharacterValidator(),
                new SpacesValidator(),
                new ContainNumberValidator(),
                new SpecialCharacterValidator(),
                new MinLengthValidator()
        );

        return new User.Builder()
                .id(UUID.randomUUID())
                .cpfcnpj(new CpfCnpj("12345678909"))
                .name(new Name("Marcos", "Chalet"))
                .email(new Email("marcos@example.com"))
                .password(Password.create("myPa33word#V3", passwordValidators, passwordEncoder))
                .companyId(UUID.randomUUID())
                .roles(Set.of(Role.ROLE_ADMIN))
                .active(true);
    }


    private static Stream<User.Builder> invalidUserBuilders() {
        return Stream.of(
                validUserBuilder().cpfcnpj(null), // Missing CPF/CNPJ
                validUserBuilder().name(null),    // Missing Name
                validUserBuilder().email(null),   // Missing Email
                validUserBuilder().password(null),// Missing Password
                validUserBuilder().companyId(null),// Missing CompanyId
                validUserBuilder().roles(null)    // Missing Roles
        );
    }

    @ParameterizedTest
    @MethodSource("invalidUserBuilders")
    void shouldThrowExceptionIfUserNotHaveRequiredFields(User.Builder builder) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                builder::build
        );

        assertEquals("Required fields are missing.", exception.getMessage());
    }

    @Test
    void shouldUpdateUserEmailSuccess() {
        when(passwordEncoder.encode("myPassword")).thenReturn("hashed-myPassword");

        Email email = new Email("marcos@example.com");
        Password password = Password.create("myPassword", List.of(passwordValidator), passwordEncoder);

        User user = new User.Builder()
                .cpfcnpj(new CpfCnpj("12345678909"))
                .name(new Name("Marcos", "Chalet"))
                .email(new Email("marcos_teste@example.com"))
                .password(password)
                .companyId(UUID.randomUUID())
                .roles(Set.of(Role.ROLE_ADMIN))
                .active(true)
                .build();

        user.updateEmail(email);
        assertEquals(email.value(), user.email());
    }
}
