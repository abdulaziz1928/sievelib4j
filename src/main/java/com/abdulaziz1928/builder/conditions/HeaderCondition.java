package com.abdulaziz1928.builder.conditions;

import com.abdulaziz1928.builder.types.MatchType;
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
