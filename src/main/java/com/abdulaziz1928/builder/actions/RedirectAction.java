package com.abdulaziz1928.builder.actions;

import lombok.Getter;

import java.util.Objects;

@Getter
public class RedirectAction extends SieveAction {
    final String address;
    final boolean copy;

    public RedirectAction(String address, boolean copy) {
        this.address = Objects.requireNonNull(address, "address name is required");
        this.copy = copy;
    }

    public RedirectAction(String address) {
        this(address, false);
    }
}
