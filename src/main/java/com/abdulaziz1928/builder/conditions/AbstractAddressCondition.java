package com.abdulaziz1928.builder.conditions;

import com.abdulaziz1928.builder.types.AddressPart;
import com.abdulaziz1928.builder.types.MatchType;
import lombok.Getter;


import java.util.Objects;
@Getter
public abstract class AbstractAddressCondition extends SieveCondition {
    private final AddressPart addressPart;

    private final MatchType matchType;

    protected AbstractAddressCondition(AddressPart addressPart, MatchType matchType) {
        this.addressPart = addressPart;
        this.matchType = Objects.requireNonNull(matchType);
    }
}
