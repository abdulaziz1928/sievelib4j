package com.abdulaziz1928.builder.actions;

import com.abdulaziz1928.builder.SieveArgument;
import com.abdulaziz1928.builder.control.ControlRequire;
import lombok.Getter;

@Getter
public abstract class CustomSieveAction extends SieveAction {
    private final ControlRequire imports = new ControlRequire();

    protected void applyImport(String capability) {
        imports.addCapability(capability);
    }
    public abstract SieveArgument generateAction();

}
