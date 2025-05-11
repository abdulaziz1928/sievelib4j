package com.abdulaziz1928.builder.control;

import com.abdulaziz1928.builder.actions.SieveAction;
import com.abdulaziz1928.builder.conditions.SieveCondition;
import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class ControlIf extends SieveControl {
    final SieveCondition condition;
    final List<SieveAction> actions;
}
