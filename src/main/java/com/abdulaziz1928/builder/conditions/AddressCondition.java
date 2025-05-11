package com.abdulaziz1928.builder.conditions;

import com.abdulaziz1928.builder.types.AddressPart;
import com.abdulaziz1928.builder.types.MatchType;
import lombok.Getter;


import java.util.List;

@Getter
public class AddressCondition extends AbstractAddressCondition {
    private final List<String> headers;
    private final List<String> keys;

    public AddressCondition(MatchType matchType, AddressPart addressPart, List<String> headers, List<String> keys) {
        super(addressPart, matchType);
        this.headers = headers;
        this.keys = keys;
    }

}

