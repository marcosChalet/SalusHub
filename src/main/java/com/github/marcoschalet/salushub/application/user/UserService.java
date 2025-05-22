package com.github.marcoschalet.salushub.application.user;

import com.github.marcoschalet.salushub.api.user.dto.UserRequestDTO;
import com.github.marcoschalet.salushub.domain.user.*;
import com.github.marcoschalet.salushub.api.user.validator.PasswordValidator;
import com.github.marcoschalet.salushub.infrastructure.persistence.user.JpaUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final PasswordEncoder encoder;
    private final JpaUserRepository userRepository;
    private final List<PasswordValidator> passwordValidators;

    public UserService(PasswordEncoder encoder, JpaUserRepository userRepository, List<PasswordValidator> passwordValidators) {
        this.encoder = encoder;
        this.passwordValidators = passwordValidators;
        this.userRepository = userRepository;
    }

    public User registerUser(UserRequestDTO userRequest) {
        Password password = Password.create(userRequest.getPassword(), passwordValidators, encoder);

        User user = new User.Builder()
                .cpfcnpj(new CpfCnpj(userRequest.getCpfcnpj()))
                .name(new Name(userRequest.getFirstName(), userRequest.getLastName()))
                .email(new Email(userRequest.getEmail()))
                .password(password)
                .companyId(userRequest.getCompanyId())
                .roles(userRequest.getRoles())
                .active(true)
                .build();

        return userRepository.save(user);
    }

    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    public User findByEmail(Email email) {
        return userRepository.findByEmail(email.value())
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email.value()));
    }

    public void changeUserPassword(User user, String newRawPassword) {
        Password newPassword = Password.create(newRawPassword, passwordValidators, encoder);
        user.changePassword(newPassword);
    }
}
