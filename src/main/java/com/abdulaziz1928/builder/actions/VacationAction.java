package com.abdulaziz1928.builder.actions;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class VacationAction extends SieveAction {
    private final Integer days;
    private final String subject;
    private final String from;
    private final List<String> addresses;
    private final String mime;
    private final String handle;
    private final String reason;

    @Builder
    public VacationAction(Integer days, String subject, String from, List<String> addresses, String mime, String handle, String reason) {
        this.days = days;
        this.subject = subject;
        this.from = from;
        this.addresses = Objects.requireNonNullElse(addresses, new ArrayList<>());
        this.mime = mime;
        this.handle = handle;
        this.reason = Objects.requireNonNull(reason,"reason is required");
    }
}
