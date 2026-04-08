package com.petadoption.patterns.auth.factory;

import com.petadoption.dto.SignupRequestDTO;
import com.petadoption.model.Admin;
import com.petadoption.model.User;
import org.springframework.stereotype.Component;

@Component
public class AdminProfileFactory implements RoleProfileFactory {

    @Override
    public boolean supports(User.Role role) {
        return role == User.Role.Admin;
    }

    @Override
    public void attachProfile(User user, SignupRequestDTO request) {
        Admin admin = new Admin();
        admin.setUser(user);
        admin.setDepartment(hasText(request.getDepartment()) ? request.getDepartment().trim() : "General Administration");
        admin.setPermissions(hasText(request.getPermissions()) ? request.getPermissions().trim() : "manage-system");
        user.setAdmin(admin);
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
