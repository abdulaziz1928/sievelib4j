package com.abdulaziz1928.sievelib4j.builder.conditions;

import com.abdulaziz1928.sievelib4j.builder.SieveArgument;
import com.abdulaziz1928.sievelib4j.builder.control.ControlRequire;
import lombok.Getter;


@Getter
public abstract class CustomSieveCondition extends SieveCondition {
    private final ControlRequire imports = new ControlRequire();

    protected void applyImport(String capability) {
        imports.addCapability(capability);
    }

    public abstract SieveArgument generateCondition();
}
