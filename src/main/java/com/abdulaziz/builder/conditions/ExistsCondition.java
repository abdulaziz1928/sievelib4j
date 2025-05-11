package com.abdulaziz.builder.conditions;

import lombok.Getter;

import java.util.List;
@Getter
public class ExistsCondition extends SieveCondition{
    final List<String> headers;

    public ExistsCondition(List<String> headers) {
        this.headers = headers;
    }
}
