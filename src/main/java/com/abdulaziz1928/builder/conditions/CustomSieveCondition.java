package com.abdulaziz1928.builder.conditions;

import com.abdulaziz1928.builder.SieveArgument;
import com.abdulaziz1928.builder.control.ControlRequire;
import lombok.Getter;


@Getter
public abstract class CustomSieveCondition extends SieveCondition {
    private final ControlRequire imports = new ControlRequire();

    protected void applyImport(String capability) {
        imports.addCapability(capability);
    }

    public abstract SieveArgument generateCondition();
}
