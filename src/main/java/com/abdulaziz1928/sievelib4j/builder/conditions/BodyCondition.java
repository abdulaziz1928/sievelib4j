package com.abdulaziz1928.sievelib4j.builder.conditions;

import com.abdulaziz1928.sievelib4j.builder.SieveUtils;
import com.abdulaziz1928.sievelib4j.builder.types.BodyTransform;
import com.abdulaziz1928.sievelib4j.builder.types.Comparator;
import com.abdulaziz1928.sievelib4j.builder.types.Match;
import lombok.Getter;

import java.util.List;

@Getter
public class BodyCondition extends SieveCondition {
    private final Comparator comparator;
    private final Match match;
    private final BodyTransform bodyTransform;
    private final List<String> keys;

    public BodyCondition(Comparator comparator, Match match, BodyTransform bodyTransform, List<String> keys) {
        this.comparator = comparator;
        this.match = match;
        this.bodyTransform = bodyTransform;
        this.keys = SieveUtils.requiredParamList(keys, "key-list is required");
    }

    public BodyCondition(Match match, BodyTransform bodyTransform, List<String> keys) {
        this(null, match, bodyTransform, keys);
    }
}
