package com.abdulaziz1928.builder.actions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FileIntoAction extends SieveAction {
    final String mailbox;
    final boolean copy;

    public FileIntoAction(String mailbox) {
        this.mailbox = mailbox;
        this.copy = false;
    }
}
