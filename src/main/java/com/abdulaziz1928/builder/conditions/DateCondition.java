package com.abdulaziz1928.builder.conditions;

import com.abdulaziz1928.builder.types.*;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class DateCondition extends SieveCondition {
    private final DateZone dateZone;
    private final Index index;
    private final Comparator comparator;
    private final Match match;
    private final String header;
    private final DatePart datePart;
    private final List<String> keys;

    private DateCondition(Index index, Comparator comparator, DateZone dateZone, Match match, String header, DatePart datePart, List<String> keys) {
        this.index = index;
        this.comparator = comparator;
        this.dateZone = dateZone;
        this.match = match;
        this.header = Objects.requireNonNull(header, "header name is required");
        this.datePart = Objects.requireNonNull(datePart, "date part is required");
        this.keys = Objects.requireNonNull(keys, "key-list is required");
    }

    public DateCondition(Comparator comparator, DateZone dateZone, Match match, String header, DatePart datePart, List<String> keys) {
        this(null, comparator, dateZone, match, header, datePart, keys);
    }

    public DateCondition(DateZone dateZone, Match match, String header, DatePart datePart, List<String> keys) {
        this(null, dateZone, match, header, datePart, keys);
    }

    public DateCondition withIndex(Index index) {
        return Objects.equals(this.index, index) ? this : new DateCondition(index, getComparator(), getDateZone(), getMatch(), getHeader(), getDatePart(), getKeys());
    }
}
