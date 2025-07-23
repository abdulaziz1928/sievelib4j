package com.abdulaziz1928.builder.conditions;

import com.abdulaziz1928.builder.SieveUtils;
import lombok.Getter;


@Getter
public class NotCondition extends SieveCondition {
    private final SieveCondition condition;

    public NotCondition(SieveCondition condition) {
        this.condition = SieveUtils.requiredParam(condition,"condition is required");
    }
}
