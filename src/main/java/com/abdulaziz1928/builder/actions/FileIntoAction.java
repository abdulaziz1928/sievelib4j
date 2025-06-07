package com.abdulaziz1928.builder.actions;

import lombok.Getter;

import java.util.Objects;

@Getter
public class FileIntoAction extends SieveAction {
    final String mailbox;
    final boolean copy;

    public FileIntoAction(String mailbox, boolean copy) {
        this.mailbox = Objects.requireNonNull(mailbox, "mailbox name is required");
        this.copy = copy;
    }

    public FileIntoAction(String mailbox) {
        this(mailbox, false);
    }
}
