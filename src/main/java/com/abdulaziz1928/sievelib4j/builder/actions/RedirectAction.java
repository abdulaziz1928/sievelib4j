package com.abdulaziz1928.sievelib4j.builder.actions;

import com.abdulaziz1928.sievelib4j.builder.SieveUtils;
import lombok.Getter;

@Getter
public class RedirectAction extends SieveAction {
    final String address;
    final boolean copy;

    public RedirectAction(String address, boolean copy) {
        this.address = SieveUtils.requiredParam(address, "address name is required");
        this.copy = copy;
    }

    public RedirectAction(String address) {
        this(address, false);
    }
}
