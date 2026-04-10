package com.petadoption.designpatterns.chain;

import com.petadoption.dto.SignupRequestDTO;
import com.petadoption.model.User;

public class SignupValidationContext {
    private final SignupRequestDTO request;
    private User.Role resolvedRole;

    public SignupValidationContext(SignupRequestDTO request) {
        this.request = request;
    }

    public SignupRequestDTO getRequest() {
        return request;
    }

    public User.Role getResolvedRole() {
        return resolvedRole;
    }

    public void setResolvedRole(User.Role resolvedRole) {
        this.resolvedRole = resolvedRole;
    }
}
