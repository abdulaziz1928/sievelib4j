package com.abdulaziz1928.builder.conditions;

import com.abdulaziz1928.builder.SieveUtils;
import com.abdulaziz1928.builder.types.Comparator;
import com.abdulaziz1928.builder.types.Match;
import lombok.Getter;

import java.util.List;

@Getter
public class HasFlagCondition extends SieveCondition {
    private final Match match;
    private final Comparator comparator;
    private final List<String> variables;
    private final List<String> flags;

    public HasFlagCondition(Comparator comparator, Match match, List<String> variables, List<String> flags) {
        this.match = match;
        this.comparator = comparator;
        this.variables = variables;
        this.flags = SieveUtils.requiredParam(flags, "flag-list is required");
    }

    public HasFlagCondition(Match match, List<String> variables, List<String> flags) {
        this(null, match, variables, flags);
    }

    public HasFlagCondition(Match match, List<String> flags) {
        this(null, match, null, flags);
    }

    public HasFlagCondition(Comparator comparator, Match match, List<String> flags) {
        this(comparator, match, null, flags);
    }
}
