package com.petadoption.controller;

import com.petadoption.model.Adopter;
import com.petadoption.model.User;
import com.petadoption.service.AuthService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    // LOGIN
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestParam String username,
                                   @RequestParam String password) {
        User user = authService.login(username, password);
        if (user != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("username", user.getUsername());
            response.put("role", user.getRole().name());
            response.put("userId", user.getUserId());
            response.put("adopterId", user.getAdopter() != null ? user.getAdopter().getAdopterId() : null);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
    
    // SIGNUP
    // Add this inner class or create a separate file
    record SignupRequest(String username,
                         String password,
                         String role,
                         String name,
                         String phone,
                         String address,
                         String experienceLevel) {}

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

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        try {
            User user = new User();
            user.setUsername(request.username());
            user.setPassword(request.password());
            user.setRole(parseRole(request.role()));
            user.setCreatedAt(java.time.LocalDateTime.now());

            if (user.getRole() == User.Role.Adopter) {
                Adopter adopter = new Adopter();
                adopter.setUser(user);
                adopter.setName(request.name() != null && !request.name().isBlank() ? request.name() : request.username());
                adopter.setPhone(request.phone() != null && !request.phone().isBlank() ? request.phone() : "0000000000");
                adopter.setAddress(request.address() != null && !request.address().isBlank() ? request.address() : "Address not provided");
                adopter.setExperienceLevel(request.experienceLevel() != null && !request.experienceLevel().isBlank() ? request.experienceLevel() : "Beginner");
                user.setAdopter(adopter);
            }

            authService.signup(user);
            return ResponseEntity.ok("Signup successful!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Signup failed: " + e.getMessage());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(409).body("Signup failed: Username already exists");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Signup failed: " + e.getMessage());
        }
    }
}