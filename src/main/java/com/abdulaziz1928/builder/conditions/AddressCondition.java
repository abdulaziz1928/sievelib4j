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
    private final Boolean mime;
    private final Boolean anyChild;

    public AddressCondition(Match match, AddressPart addressPart, List<String> headers, List<String> keys) {
        this(null, match, addressPart, headers, keys);
    }

    public AddressCondition(Comparator comparator, Match match, AddressPart addressPart, List<String> headers, List<String> keys) {
        this(null, null, comparator, addressPart, headers, keys, null, match);
    }

    public AddressCondition(Boolean mime, Boolean anyChild, Comparator comparator, AddressPart addressPart, List<String> headers, List<String> keys, Index index, Match match) {
        super(addressPart, match, comparator);
        this.headers = Objects.requireNonNull(headers, "header-list is required");
        this.keys = Objects.requireNonNull(keys, "key-list is required");
        this.index = index;
        this.mime = mime;
        this.anyChild = anyChild;
    }

    public AddressCondition(Boolean mime, Boolean anyChild, Comparator comparator, AddressPart addressPart, Match match, List<String> headers, List<String> keys) {
        this(mime, anyChild, comparator, addressPart, headers, keys, null, match);
    }

    public AddressCondition withIndex(Index index) {
        return Objects.equals(this.index, index) ? this : new AddressCondition(getMime(), getAnyChild(), getComparator(), getAddressPart(), getHeaders(), getKeys(), index, getMatch());
    }
}

