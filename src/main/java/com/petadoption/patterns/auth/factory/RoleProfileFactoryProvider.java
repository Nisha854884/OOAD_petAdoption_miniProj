package com.petadoption.patterns.auth.factory;

import com.petadoption.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleProfileFactoryProvider {

    private final List<RoleProfileFactory> factories;

    public RoleProfileFactoryProvider(List<RoleProfileFactory> factories) {
        this.factories = factories;
    }

    public RoleProfileFactory getFactory(User.Role role) {
        return factories.stream()
                .filter(factory -> factory.supports(role))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No factory registered for role: " + role));
    }
}
