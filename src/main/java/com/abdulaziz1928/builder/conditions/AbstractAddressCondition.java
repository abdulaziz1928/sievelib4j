package com.abdulaziz1928.builder.conditions;

import com.abdulaziz1928.builder.types.AddressPart;
import com.abdulaziz1928.builder.types.Comparator;
import com.abdulaziz1928.builder.types.Match;
import lombok.Getter;

@Getter
public abstract class AbstractAddressCondition extends SieveCondition {
    private final AddressPart addressPart;
    private final Match match;
    private final Comparator comparator;

    protected AbstractAddressCondition(AddressPart addressPart, Match match, Comparator comparator) {
        this.addressPart = addressPart;
        this.match = match;
        this.comparator = comparator;
    }
}
