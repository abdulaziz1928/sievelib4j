package com.abdulaziz1928.builder.control;

import com.abdulaziz1928.builder.SieveUtils;
import com.abdulaziz1928.builder.actions.SieveAction;
import com.abdulaziz1928.builder.conditions.SieveCondition;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public final class ControlIf extends SieveControl {
    private final SieveCondition condition;
    private final List<SieveAction> actions;

    @Builder
    public ControlIf(SieveCondition condition, List<SieveAction> actions) {
        this.condition = SieveUtils.requiredParam(condition, "condition is required");
        this.actions = SieveUtils.requiredParamList(actions, "actions are required");
    }
}
