package com.abdulaziz1928.builder.control;

import com.abdulaziz1928.builder.actions.SieveAction;
import com.abdulaziz1928.builder.conditions.SieveCondition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class ControlElseIf extends SieveControl {
    final SieveCondition condition;
    final List<SieveAction> actions;
}
