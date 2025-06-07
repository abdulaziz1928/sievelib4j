package com.abdulaziz1928.builder.actions;

import lombok.Getter;

import java.util.List;

@Getter
public class RemoveFlagAction extends AbstractFlagAction {

    public RemoveFlagAction(List<String> flags) {
        this(null, flags);
    }

    public RemoveFlagAction(String variableName, List<String> flags) {
        super(variableName, flags);
    }}
