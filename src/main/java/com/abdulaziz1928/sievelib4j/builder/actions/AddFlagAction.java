package com.abdulaziz1928.sievelib4j.builder.actions;

import java.util.List;

public class AddFlagAction extends AbstractFlagAction {

    public AddFlagAction(List<String> flags) {
        this(null, flags);
    }

    public AddFlagAction(String variableName, List<String> flags) {
        super(variableName, flags);
    }
}
