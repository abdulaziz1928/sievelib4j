package com.abdulaziz1928.builder.actions;

import com.abdulaziz1928.builder.SieveUtils;
import lombok.Getter;

import java.util.List;

@Getter
public class FileIntoAction extends SieveAction {
    private final String mailbox;
    private final boolean copy;
    private final List<String> flags;
    private final String flagsVariableName;

    public FileIntoAction(String mailbox, boolean copy) {
        this(mailbox, copy, null, null);
    }

    public FileIntoAction(String mailbox, boolean copy, List<String> flags) {
        this(mailbox, copy, null, flags);
    }

    public FileIntoAction(String mailbox, boolean copy, String flagsVariableName) {
        this(mailbox, copy, flagsVariableName, null);
    }

    public FileIntoAction(String mailbox, List<String> flags) {
        this(mailbox, false, null, flags);
    }

    public FileIntoAction(String mailbox, String flagsVariableName) {
        this(mailbox, false, flagsVariableName, null);

    }

    public FileIntoAction(String mailbox) {
        this(mailbox, false, null, null);
    }

    private FileIntoAction(String mailbox, boolean copy, String flagsVariableName, List<String> flags) {
        this.mailbox = SieveUtils.requiredParam(mailbox, "mailbox name is required");
        this.copy = copy;
        this.flagsVariableName = flagsVariableName;
        this.flags = flags;

    }
}
