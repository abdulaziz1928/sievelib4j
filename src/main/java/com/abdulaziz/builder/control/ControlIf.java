package com.abdulaziz.builder.control;

import com.abdulaziz.builder.actions.SieveAction;
import com.abdulaziz.builder.conditions.SieveCondition;
import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class ControlIf extends SieveControl {
    final SieveCondition condition;
    final List<SieveAction> actions;
}
