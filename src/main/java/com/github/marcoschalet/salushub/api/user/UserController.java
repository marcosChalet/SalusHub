package com.github.marcoschalet.salushub.api.user;

import com.github.marcoschalet.salushub.api.user.dto.UserResponseMapper;
import com.github.marcoschalet.salushub.application.user.UserService;
import com.github.marcoschalet.salushub.api.user.dto.UserResponseDTO;
import com.github.marcoschalet.salushub.api.user.dto.UserRequestDTO;
import com.github.marcoschalet.salushub.domain.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO user) {
        User userDomain = userService.registerUser(user);
        UserResponseDTO response = UserResponseMapper.from(userDomain);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable UUID id) {
        User userDomain = userService.findById(id);
        UserResponseDTO response = UserResponseMapper.from(userDomain);
        return ResponseEntity.ok(response);
    }
}