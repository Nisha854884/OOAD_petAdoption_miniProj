package com.petadoption.patterns.auth.factory;

import com.petadoption.dto.SignupRequestDTO;
import com.petadoption.model.Shelter;
import com.petadoption.model.Staff;
import com.petadoption.model.User;
import com.petadoption.repository.ShelterRepository;
import org.springframework.stereotype.Component;

@Component
public class StaffProfileFactory implements RoleProfileFactory {

    private final ShelterRepository shelterRepository;

    public StaffProfileFactory(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    @Override
    public boolean supports(User.Role role) {
        return role == User.Role.Staff;
    }

    @Override
    public void attachProfile(User user, SignupRequestDTO request) {
        // Staff details are optional during signup. If complete fields are provided, create the profile.
        if (!hasText(request.getPosition()) || !hasText(request.getPhone()) || !hasText(request.getEmail()) || request.getShelterId() == null) {
            return;
        }

        Shelter shelter = shelterRepository.findById(request.getShelterId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid shelterId for staff signup"));

        Staff staff = new Staff();
        staff.setUser(user);
        staff.setPosition(request.getPosition().trim());
        staff.setPhone(request.getPhone().trim());
        staff.setEmail(request.getEmail().trim());
        staff.setShelter(shelter);
        user.setStaff(staff);
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
