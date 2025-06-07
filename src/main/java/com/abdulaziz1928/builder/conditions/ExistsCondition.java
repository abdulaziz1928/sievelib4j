package com.abdulaziz1928.builder.conditions;

import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class ExistsCondition extends SieveCondition {
    final List<String> headers;

    public ExistsCondition(List<String> headers) {
        this.headers = Objects.requireNonNull(headers, "header-list is required");
    }
}
