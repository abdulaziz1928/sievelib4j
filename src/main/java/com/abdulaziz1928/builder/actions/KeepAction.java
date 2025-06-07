package com.abdulaziz1928.builder.actions;

import lombok.Getter;

import java.util.List;

@Getter
public class KeepAction extends SieveAction {
    private final List<String> flags;
    private final String flagsVariableName;

    public KeepAction() {
        this.flags = null;
        this.flagsVariableName = null;
    }

    public KeepAction(List<String> flags) {
        this.flags = flags;
        this.flagsVariableName = null;
    }

    public KeepAction(String flagsVariableName) {
        this.flagsVariableName = flagsVariableName;
        this.flags = null;
    }
}
