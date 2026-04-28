package com.abdulaziz1928.sievelib4j.builder;

import com.abdulaziz1928.sievelib4j.builder.control.ControlRequire;
import lombok.Getter;

@Getter
abstract class SieveEnclosingBlock {
    private final ControlRequire imports = new ControlRequire();

    protected void applyImport(String capability) {
        imports.addCapability(capability);
    }


    abstract SieveArgument getBlockValue();
}
