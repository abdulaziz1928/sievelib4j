package com.abdulaziz1928.builder.conditions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

// anyof
@Getter
@AllArgsConstructor
public class OrCondition extends SieveCondition {
    private final List<SieveCondition> conditions;

    public OrCondition(SieveCondition... conditions) {
        if (conditions.length <= 1)
            throw new IllegalArgumentException("more than one condition must be provided");
        this.conditions = Arrays.stream(conditions).toList();
    }
}
