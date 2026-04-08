package com.petadoption.designpatterns.chain;

public abstract class BaseSignupValidationHandler implements SignupValidationHandler {
    private SignupValidationHandler next;

    @Override
    public SignupValidationHandler setNext(SignupValidationHandler next) {
        this.next = next;
        return next;
    }

    @Override
    public void handle(SignupValidationContext context) {
        validate(context);
        if (next != null) {
            next.handle(context);
        }
    }

    protected abstract void validate(SignupValidationContext context);
}
