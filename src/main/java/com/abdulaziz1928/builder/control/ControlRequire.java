package com.abdulaziz1928.builder.control;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@Getter
public final class ControlRequire {
    private final Set<String> capabilities = new HashSet<>();

    public void addCapability(String capability) {
        capabilities.add(capability);
    }

    public void addCapabilities(Collection<String> capabilities) {
        this.capabilities.addAll(capabilities);
    }
}
