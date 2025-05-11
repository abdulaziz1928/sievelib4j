package com.abdulaziz1928.builder.actions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class VacationAction extends SieveAction {
    private final Integer days;
    private final String subject;
    private final String from;
    private final List<String> addresses;
    private final String mime;
    private final String handle;
    private final String reason;
}
