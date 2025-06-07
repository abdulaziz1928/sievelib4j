package com.abdulaziz1928.builder.actions;

import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class AbstractFlagAction extends SieveAction {
    private final List<String> flags;
    private final String variableName;

    public AbstractFlagAction(String variableName, List<String> flags) {
        this.flags = Objects.requireNonNull(flags, "flag-list is required");
        this.variableName = variableName;
    }
}
