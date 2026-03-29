package com.petadoption.controller;

import com.petadoption.model.User;
import com.petadoption.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // LOGIN
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password) {

        User user = authService.login(username, password);

        if (user != null) {
            return "Login successful! Role: " + user.getRole();
        } else {
            return "Invalid credentials";
        }
    }

    // SIGNUP
    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        authService.signup(user);
        return "Signup successful!";
    }
}