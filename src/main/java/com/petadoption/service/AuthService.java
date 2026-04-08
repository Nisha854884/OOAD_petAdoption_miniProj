package com.petadoption.service;

import com.petadoption.model.User;
import com.petadoption.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    // LOGIN
    public User login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    // SIGNUP
    public User signup(User user) {
        if (user.getRole() == null) {
            user.setRole(User.Role.Adopter);
        }
        return userRepository.save(user);
    }
}