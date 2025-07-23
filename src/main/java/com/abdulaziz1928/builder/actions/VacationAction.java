package com.abdulaziz1928.builder.actions;

import com.abdulaziz1928.builder.exceptions.SieveRequiredParamException;
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
        if (Objects.isNull(mime) && Objects.isNull(reason)) {
            throw new SieveRequiredParamException("Vacation action requires either mime or reason argument to be present");
        }
        if (Objects.nonNull(mime) && Objects.nonNull(reason)) {
            throw new SieveRequiredParamException("Only one of mime or reason may be specified in the vacation action");
        }
        this.days = days;
        this.subject = subject;
        this.from = from;
        this.addresses = Objects.requireNonNullElse(addresses, new ArrayList<>());
        this.mime = mime;
        this.handle = handle;
        this.reason = reason;
    }
}
