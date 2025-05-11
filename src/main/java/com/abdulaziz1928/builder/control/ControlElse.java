package com.abdulaziz1928.builder.control;

import com.abdulaziz1928.builder.actions.SieveAction;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ControlElse extends SieveControl {
    final List<SieveAction> actions;

}
