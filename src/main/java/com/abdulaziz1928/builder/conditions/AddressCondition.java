package com.abdulaziz1928.builder.conditions;

import com.abdulaziz1928.builder.types.AddressPart;
import com.abdulaziz1928.builder.types.Comparator;
import com.abdulaziz1928.builder.types.Index;
import com.abdulaziz1928.builder.types.Match;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class AddressCondition extends AbstractAddressCondition {
    private final List<String> headers;
    private final List<String> keys;
    private final Index index;

    public AddressCondition(Match match, AddressPart addressPart, List<String> headers, List<String> keys) {
        this(null, match, addressPart, headers, keys);
    }

    public AddressCondition(Comparator comparator, Match match, AddressPart addressPart, List<String> headers, List<String> keys) {
        this(null, comparator, match, addressPart, headers, keys);
    }

    public AddressCondition(Index index, Comparator comparator, Match match, AddressPart addressPart, List<String> headers, List<String> keys) {
        super(addressPart, match, comparator);
        this.headers = Objects.requireNonNull(headers, "header-list is required");
        this.keys = Objects.requireNonNull(keys, "key-list is required");
        this.index = index;
    }

    public AddressCondition withIndex(Index index) {
        return Objects.equals(this.index, index) ? this : new AddressCondition(index, getComparator(), getMatch(), getAddressPart(), getHeaders(), getKeys());
    }

}

