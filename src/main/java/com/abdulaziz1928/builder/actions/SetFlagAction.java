package com.abdulaziz1928.builder.actions;

import java.util.List;

public class SetFlagAction extends AbstractFlagAction {
    public SetFlagAction(List<String> flags) {
        this(null, flags);
    }

    public SetFlagAction(String variableName, List<String> flags) {
        super(variableName, flags);
    }
}
