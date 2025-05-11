package com.abdulaziz.builder.control;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@Getter
public class ControlRequire {
    private final Set<String> capabilities = new HashSet<>();

    public void addCapability(String capability) {
        capabilities.add(capability);
    }
}
