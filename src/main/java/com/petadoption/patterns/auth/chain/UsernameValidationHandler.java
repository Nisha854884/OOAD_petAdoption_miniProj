package com.petadoption.patterns.auth.chain;

import com.petadoption.dto.SignupRequestDTO;
import com.petadoption.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UsernameValidationHandler extends SignupValidationHandler {

    private final UserRepository userRepository;

    public UsernameValidationHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void validate(SignupRequestDTO request) {
        String username = request.getUsername() == null ? "" : request.getUsername().trim();

        if (username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be blank");
        }

        if (username.length() < 3 || username.length() > 50) {
            throw new IllegalArgumentException("Username must be between 3 and 50 characters");
        }

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
    }
}
