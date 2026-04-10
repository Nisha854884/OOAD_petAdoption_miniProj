package com.petadoption.patterns.auth.chain;

import com.petadoption.dto.SignupRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class RoleValidationHandler extends SignupValidationHandler {

    @Override
    protected void validate(SignupRequestDTO request) {
        String role = request.getRole();
        if (role == null || role.isBlank()) {
            return;
        }

        String normalizedRole = role.trim().toUpperCase();
        if (!normalizedRole.equals("ADMIN") && !normalizedRole.equals("STAFF") && !normalizedRole.equals("ADOPTER")) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }
}
