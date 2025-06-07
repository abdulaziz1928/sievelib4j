package com.abdulaziz1928.builder.actions;

import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class AbstractFlagAction extends SieveAction {
    private final List<String> flags;

    public AbstractFlagAction(List<String> flags) {
        this.flags = Objects.requireNonNull(flags,"flag-list is required");
    }
}
