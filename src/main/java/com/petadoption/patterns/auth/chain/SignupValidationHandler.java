package com.petadoption.patterns.auth.chain;

import com.petadoption.dto.SignupRequestDTO;

public abstract class SignupValidationHandler {

    private SignupValidationHandler next;

    public SignupValidationHandler setNext(SignupValidationHandler next) {
        this.next = next;
        return next;
    }

    public final void handle(SignupRequestDTO request) {
        validate(request);
        if (next != null) {
            next.handle(request);
        }
    }

    protected abstract void validate(SignupRequestDTO request);
}
