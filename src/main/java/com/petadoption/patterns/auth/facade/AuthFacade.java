package com.petadoption.patterns.auth.facade;

import com.petadoption.dto.SignupRequestDTO;
import com.petadoption.model.User;
import com.petadoption.patterns.auth.chain.SignupValidationChain;
import com.petadoption.patterns.auth.factory.RoleProfileFactoryProvider;
import com.petadoption.service.AuthService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuthFacade {

    private final AuthService authService;
    private final SignupValidationChain signupValidationChain;
    private final RoleProfileFactoryProvider roleProfileFactoryProvider;

    public AuthFacade(AuthService authService,
                      SignupValidationChain signupValidationChain,
                      RoleProfileFactoryProvider roleProfileFactoryProvider) {
        this.authService = authService;
        this.signupValidationChain = signupValidationChain;
        this.roleProfileFactoryProvider = roleProfileFactoryProvider;
    }

    public User login(String username, String password) {
        return authService.login(username, password);
    }

    public User signup(SignupRequestDTO request) {
        signupValidationChain.validate(request);

        User user = new User();
        user.setUsername(request.getUsername().trim());
        user.setPassword(request.getPassword());
        user.setRole(parseRole(request.getRole()));
        user.setCreatedAt(LocalDateTime.now());

        roleProfileFactoryProvider.getFactory(user.getRole()).attachProfile(user, request);
        return authService.signup(user);
    }

    private User.Role parseRole(String role) {
        if (role == null || role.isBlank()) {
            return User.Role.Adopter;
        }
        return switch (role.trim().toUpperCase()) {
            case "ADMIN" -> User.Role.Admin;
            case "STAFF" -> User.Role.Staff;
            case "ADOPTER" -> User.Role.Adopter;
            default -> throw new IllegalArgumentException("Invalid role: " + role);
        };
    }
}
