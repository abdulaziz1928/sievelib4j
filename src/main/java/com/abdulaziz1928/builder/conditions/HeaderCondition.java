package com.abdulaziz1928.builder.conditions;

import com.abdulaziz1928.builder.types.Comparator;
import com.abdulaziz1928.builder.types.MatchType;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class HeaderCondition extends SieveCondition {
    private final Comparator comparator;
    private final MatchType matchType;
    private final List<String> headers;
    private final List<String> keys;

    public HeaderCondition(Comparator comparator, MatchType matchType, List<String> headers, List<String> keys) {
        this.comparator = comparator;
        this.matchType = matchType;
        this.headers = Objects.requireNonNull(headers, "header-list is required");
        this.keys = Objects.requireNonNull(keys, "key-list is required");
    }

    public HeaderCondition(MatchType matchType, List<String> headers, List<String> keys) {
        this(null, matchType, headers, keys);
    }
}
