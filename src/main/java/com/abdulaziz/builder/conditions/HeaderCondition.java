package com.abdulaziz.builder.conditions;

import com.abdulaziz.builder.types.MatchType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@AllArgsConstructor
@Getter
public class HeaderCondition extends SieveCondition {
    MatchType matchType;
    List<String> headers;
    List<String> keys;
}
