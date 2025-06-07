package com.abdulaziz1928.builder.conditions;

import com.abdulaziz1928.builder.types.Comparator;
import com.abdulaziz1928.builder.types.MatchType;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class HasFlagCondition extends SieveCondition {
    private final MatchType matchType;
    private final Comparator comparator;
    private final List<String> variables;
    private final List<String> flags;

    public HasFlagCondition(Comparator comparator, MatchType matchType, List<String> variables, List<String> flags) {
        this.matchType = matchType;
        this.comparator = comparator;
        this.variables = variables;
        this.flags = Objects.requireNonNull(flags,"flag-list is required");
    }

    public HasFlagCondition(MatchType matchType, List<String> variables, List<String> flags) {
        this(null, matchType, variables, flags);
    }

    public HasFlagCondition(MatchType matchType, List<String> flags) {
        this(null, matchType, null, flags);
    }
}
