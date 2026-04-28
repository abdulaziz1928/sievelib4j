package com.abdulaziz1928.sievelib4j.builder.conditions;

import com.abdulaziz1928.sievelib4j.builder.SieveUtils;
import com.abdulaziz1928.sievelib4j.builder.types.Comparator;
import com.abdulaziz1928.sievelib4j.builder.types.DatePart;
import com.abdulaziz1928.sievelib4j.builder.types.DateZone;
import com.abdulaziz1928.sievelib4j.builder.types.Match;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class CurrentDateCondition extends SieveCondition {
    private final DateZone dateZone;
    private final Comparator comparator;
    private final Match match;
    private final DatePart datePart;
    private final List<String> keys;

    public CurrentDateCondition(Comparator comparator, String zone, Match match, DatePart datePart, List<String> keys) {
        this.comparator = comparator;
        this.dateZone = Objects.nonNull(zone) ? DateZone.zone(zone) : null;
        this.match = match;
        this.datePart = SieveUtils.requiredParam(datePart, "date part is required");
        this.keys = SieveUtils.requiredParamList(keys, "key-list is required");
    }

    public CurrentDateCondition(String zone, Match match, DatePart datePart, List<String> keys) {
        this(null, zone, match, datePart, keys);
    }

}
