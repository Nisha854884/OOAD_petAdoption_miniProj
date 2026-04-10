package com.petadoption.patterns.auth.factory;

import com.petadoption.dto.SignupRequestDTO;
import com.petadoption.model.Adopter;
import com.petadoption.model.User;
import org.springframework.stereotype.Component;

@Component
public class AdopterProfileFactory implements RoleProfileFactory {

    @Override
    public boolean supports(User.Role role) {
        return role == User.Role.Adopter;
    }

    @Override
    public void attachProfile(User user, SignupRequestDTO request) {
        Adopter adopter = new Adopter();
        adopter.setUser(user);
        adopter.setName(hasText(request.getName()) ? request.getName().trim() : user.getUsername());
        adopter.setPhone(hasText(request.getPhone()) ? request.getPhone().trim() : "0000000000");
        adopter.setAddress(hasText(request.getAddress()) ? request.getAddress().trim() : "Address not provided");
        adopter.setExperienceLevel(hasText(request.getExperienceLevel()) ? request.getExperienceLevel().trim() : "Beginner");
        user.setAdopter(adopter);
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
