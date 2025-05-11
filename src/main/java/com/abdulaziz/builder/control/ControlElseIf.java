package com.abdulaziz.builder.control;

import com.abdulaziz.builder.actions.SieveAction;
import com.abdulaziz.builder.conditions.SieveCondition;
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
