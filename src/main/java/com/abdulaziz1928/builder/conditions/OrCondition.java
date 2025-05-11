package com.abdulaziz1928.builder.conditions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

// anyof
@Getter
@AllArgsConstructor
public class OrCondition extends SieveCondition{
    List<SieveCondition> conditions;

    public OrCondition(SieveCondition... conditions){
        if(conditions.length <=1)
            throw new IllegalArgumentException();
        this.conditions= Arrays.stream(conditions).toList();
    }
}
