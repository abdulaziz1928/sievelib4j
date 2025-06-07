package com.abdulaziz1928.builder.control;

import com.abdulaziz1928.builder.actions.SieveAction;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class ControlElse extends SieveControl {
    private final List<SieveAction> actions;

    @Builder
    public ControlElse(List<SieveAction> actions) {
        this.actions = Objects.requireNonNull(actions, "actions are required");
    }
}
