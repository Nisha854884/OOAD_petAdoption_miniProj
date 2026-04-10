package com.petadoption.patterns.auth.chain;

import com.petadoption.dto.SignupRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class RoleSpecificValidationHandler extends SignupValidationHandler {

    @Override
    protected void validate(SignupRequestDTO request) {
        String role = request.getRole() == null ? "ADOPTER" : request.getRole().trim().toUpperCase();

        if ("ADOPTER".equals(role)) {
            validateAdopterFields(request);
        } else if ("STAFF".equals(role)) {
            validateStaffFields(request);
        }
    }

    private void validateAdopterFields(SignupRequestDTO request) {
        String phone = valueOrEmpty(request.getPhone());
        if (!phone.isEmpty() && !phone.matches("^[0-9]{10}$")) {
            throw new IllegalArgumentException("Phone must be a 10-digit number");
        }

        String name = valueOrEmpty(request.getName());
        if (!name.isEmpty() && (name.length() < 2 || name.length() > 100)) {
            throw new IllegalArgumentException("Name must be between 2 and 100 characters");
        }

        String address = valueOrEmpty(request.getAddress());
        if (!address.isEmpty() && (address.length() < 5 || address.length() > 255)) {
            throw new IllegalArgumentException("Address must be between 5 and 255 characters");
        }
    }

    private void validateStaffFields(SignupRequestDTO request) {
        int providedCount = 0;
        if (!valueOrEmpty(request.getPosition()).isEmpty()) providedCount++;
        if (!valueOrEmpty(request.getPhone()).isEmpty()) providedCount++;
        if (!valueOrEmpty(request.getEmail()).isEmpty()) providedCount++;
        if (request.getShelterId() != null) providedCount++;

        // Staff profile creation is optional at signup, but if started, all required fields must be provided.
        if (providedCount > 0 && providedCount < 4) {
            throw new IllegalArgumentException("For staff signup, provide position, phone, email, and shelterId together");
        }

        String phone = valueOrEmpty(request.getPhone());
        if (!phone.isEmpty() && !phone.matches("^[0-9]{10}$")) {
            throw new IllegalArgumentException("Phone must be a 10-digit number");
        }

        String email = valueOrEmpty(request.getEmail());
        if (!email.isEmpty() && !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new IllegalArgumentException("Email should be valid");
        }
    }

    private String valueOrEmpty(String value) {
        return value == null ? "" : value.trim();
    }
}
