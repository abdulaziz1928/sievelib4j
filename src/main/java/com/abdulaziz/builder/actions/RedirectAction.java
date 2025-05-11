package com.abdulaziz.builder.actions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RedirectAction extends SieveAction {
    final String address;
    final boolean copy;

    RedirectAction(String address) {
        this.address = address;
        this.copy = false;
    }
}
