package com.abdulaziz.builder.conditions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NotCondition extends SieveCondition{
    final SieveCondition condition;

}
