package com.petadoption.patterns.auth.factory;

import com.petadoption.dto.SignupRequestDTO;
import com.petadoption.model.User;

public interface RoleProfileFactory {

    boolean supports(User.Role role);

    void attachProfile(User user, SignupRequestDTO request);
}
