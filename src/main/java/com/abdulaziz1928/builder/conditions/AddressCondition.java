package com.abdulaziz1928.builder.conditions;

import com.abdulaziz1928.builder.types.AddressPart;
import com.abdulaziz1928.builder.types.Comparator;
import com.abdulaziz1928.builder.types.MatchType;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class AddressCondition extends AbstractAddressCondition {
    private final List<String> headers;
    private final List<String> keys;

    public AddressCondition(MatchType matchType, AddressPart addressPart, List<String> headers, List<String> keys) {
        this(null, matchType, addressPart, headers, keys);
    }

    public AddressCondition(Comparator comparator, MatchType matchType, AddressPart addressPart, List<String> headers, List<String> keys) {
        super(addressPart, matchType, comparator);
        this.headers = Objects.requireNonNull(headers,"header-list is required");
        this.keys = Objects.requireNonNull(keys,"key-list is required");
    }

}

