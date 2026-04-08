package com.petadoption.patterns.auth.chain;

import com.petadoption.dto.SignupRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidationHandler extends SignupValidationHandler {

    @Override
    protected void validate(SignupRequestDTO request) {
        String password = request.getPassword() == null ? "" : request.getPassword();

        if (password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be blank");
        }

        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
    }
}
