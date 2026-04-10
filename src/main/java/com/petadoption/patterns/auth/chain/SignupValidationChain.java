package com.petadoption.patterns.auth.chain;

import com.petadoption.dto.SignupRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class SignupValidationChain {

    private final SignupValidationHandler first;

    public SignupValidationChain(UsernameValidationHandler usernameValidationHandler,
                                 PasswordValidationHandler passwordValidationHandler,
                                 RoleValidationHandler roleValidationHandler,
                                 RoleSpecificValidationHandler roleSpecificValidationHandler) {
        usernameValidationHandler
                .setNext(passwordValidationHandler)
                .setNext(roleValidationHandler)
                .setNext(roleSpecificValidationHandler);
        this.first = usernameValidationHandler;
    }

    public void validate(SignupRequestDTO request) {
        first.handle(request);
    }
}
