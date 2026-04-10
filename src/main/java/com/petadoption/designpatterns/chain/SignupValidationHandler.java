package com.petadoption.designpatterns.chain;

public interface SignupValidationHandler {
    SignupValidationHandler setNext(SignupValidationHandler next);

    void handle(SignupValidationContext context);
}
