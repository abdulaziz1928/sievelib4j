package com.abdulaziz1928.builder.actions;

import com.abdulaziz1928.builder.SieveUtils;
import lombok.Getter;

import java.util.List;

@Getter
public class AbstractFlagAction extends SieveAction {
    private final List<String> flags;
    private final String variableName;

    public AbstractFlagAction(String variableName, List<String> flags) {
        this.flags = SieveUtils.requiredParamList(flags, "flag-list is required");
        this.variableName = variableName;
    }
}
