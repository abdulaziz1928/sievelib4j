package com.abdulaziz.builder.control;

import com.abdulaziz.builder.actions.SieveAction;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ControlElse extends SieveControl {
    final List<SieveAction> actions;

}
