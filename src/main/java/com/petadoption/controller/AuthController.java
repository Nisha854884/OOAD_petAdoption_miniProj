package com.petadoption.controller;

import com.petadoption.dto.SignupRequestDTO;
import com.petadoption.model.User;
import com.petadoption.patterns.auth.facade.AuthFacade;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthFacade authFacade;

    // LOGIN
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestParam String username,
                                   @RequestParam String password) {
        User user = authFacade.login(username, password);
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

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<String> signup(@RequestBody SignupRequestDTO request) {
        try {
            authFacade.signup(request);
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