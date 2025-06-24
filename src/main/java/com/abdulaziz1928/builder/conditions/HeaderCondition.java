package com.abdulaziz1928.builder.conditions;

import com.abdulaziz1928.builder.types.Comparator;
import com.abdulaziz1928.builder.types.Index;
import com.abdulaziz1928.builder.types.Match;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class HeaderCondition extends SieveCondition {
    private final Comparator comparator;
    private final Match match;
    private final List<String> headers;
    private final List<String> keys;
    private final Index index;

    private HeaderCondition(Index index, Comparator comparator, Match match, List<String> headers, List<String> keys) {
        this.index = index;
        this.comparator = comparator;
        this.match = match;
        this.headers = Objects.requireNonNull(headers, "header-list is required");
        this.keys = Objects.requireNonNull(keys, "key-list is required");
    }

    public HeaderCondition(Comparator comparator, Match match, List<String> headers, List<String> keys) {
        this(null, comparator, match, headers, keys);
    }

    public HeaderCondition(Match match, List<String> headers, List<String> keys) {
        this(null, match, headers, keys);
    }

    public HeaderCondition withIndex(Index index) {
        return Objects.equals(this.index, index) ? this : new HeaderCondition(index, getComparator(), getMatch(), getHeaders(), getKeys());
    }
}
