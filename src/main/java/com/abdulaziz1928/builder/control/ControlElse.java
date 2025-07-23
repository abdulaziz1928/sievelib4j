package com.abdulaziz1928.builder.control;

import com.abdulaziz1928.builder.SieveUtils;
import com.abdulaziz1928.builder.actions.SieveAction;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public final class ControlElse extends SieveControl {
    private final List<SieveAction> actions;

    @Builder
    public ControlElse(List<SieveAction> actions) {
        this.actions = SieveUtils.requiredParamList(actions, "actions are required");
    }
}
