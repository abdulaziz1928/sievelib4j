package com.abdulaziz1928.builder.conditions;

import com.abdulaziz1928.builder.types.AddressPart;
import com.abdulaziz1928.builder.types.Comparator;
import com.abdulaziz1928.builder.types.MatchType;
import lombok.Getter;

@Getter
public abstract class AbstractAddressCondition extends SieveCondition {
    private final AddressPart addressPart;
    private final MatchType matchType;
    private final Comparator comparator;

    protected AbstractAddressCondition(AddressPart addressPart, MatchType matchType, Comparator comparator) {
        this.addressPart = addressPart;
        this.matchType = matchType;
        this.comparator = comparator;
    }
}
