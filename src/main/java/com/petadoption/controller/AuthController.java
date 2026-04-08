package com.petadoption.controller;

import com.petadoption.model.User;
import com.petadoption.service.AuthService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    // LOGIN
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestParam String username,
                                        @RequestParam String password) {
        User user = authService.login(username, password);
        if (user != null) {
            return ResponseEntity.ok("Login successful! Role: " + user.getRole());
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
    
    // SIGNUP
    // Add this inner class or create a separate file
    record SignupRequest(String username, String password) {}

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        try {
            User user = new User();
            user.setUsername(request.username());
            user.setPassword(request.password());
            user.setRole(User.Role.Adopter);
            user.setCreatedAt(java.time.LocalDateTime.now());
            authService.signup(user);
            return ResponseEntity.ok("Signup successful!");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(409).body("Signup failed: Username already exists");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Signup failed: " + e.getMessage());
        }
    }
}