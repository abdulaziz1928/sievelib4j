package com.abdulaziz1928.builder.conditions;

import com.abdulaziz1928.builder.types.AddressPart;
import com.abdulaziz1928.builder.types.Comparator;
import com.abdulaziz1928.builder.types.MatchType;
import lombok.Getter;

import java.util.List;

@Getter
public class EnvelopeCondition extends AbstractAddressCondition {
    private final List<String> envelopeParts;
    private final List<String> keys;

    public EnvelopeCondition(AddressPart addressPart, MatchType matchType, List<String> envelopeParts, List<String> keys) {
        this(null, addressPart, matchType, envelopeParts, keys);
    }

    public EnvelopeCondition(Comparator comparator, AddressPart addressPart, MatchType matchType, List<String> envelopeParts, List<String> keys) {
        super(addressPart, matchType, comparator);
        this.envelopeParts = envelopeParts;
        this.keys = keys;
    }
}
