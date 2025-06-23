package com.abdulaziz1928.builder.conditions;

import com.abdulaziz1928.builder.types.BodyTransform;
import com.abdulaziz1928.builder.types.Comparator;
import com.abdulaziz1928.builder.types.MatchType;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class BodyCondition extends SieveCondition {
    private final Comparator comparator;
    private final MatchType matchType;
    private final BodyTransform bodyTransform;
    private final List<String> keys;

    public BodyCondition(Comparator comparator, MatchType matchType, BodyTransform bodyTransform, List<String> keys) {
        this.comparator = comparator;
        this.matchType = matchType;
        this.bodyTransform = bodyTransform;
        this.keys = Objects.requireNonNull(keys, "key-list is required");
    }

    public BodyCondition(MatchType matchType, BodyTransform bodyTransform, List<String> keys) {
        this(null, matchType, bodyTransform, keys);
    }
}
