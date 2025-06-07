package com.abdulaziz1928.builder.conditions;

import lombok.Getter;

import java.util.Objects;

@Getter
public class NotCondition extends SieveCondition {
    final SieveCondition condition;

    public NotCondition(SieveCondition condition) {
        this.condition = Objects.requireNonNull(condition,"condition is required");
    }
}
