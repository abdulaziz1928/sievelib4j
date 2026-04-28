package com.abdulaziz1928.sievelib4j.builder.conditions;

import com.abdulaziz1928.sievelib4j.builder.types.AddressPart;
import com.abdulaziz1928.sievelib4j.builder.types.Comparator;
import com.abdulaziz1928.sievelib4j.builder.types.Match;
import lombok.Getter;

import java.util.List;

@Getter
public class EnvelopeCondition extends AbstractAddressCondition {
    private final List<String> envelopeParts;
    private final List<String> keys;

    public EnvelopeCondition(AddressPart addressPart, Match match, List<String> envelopeParts, List<String> keys) {
        this(null, addressPart, match, envelopeParts, keys);
    }

    public EnvelopeCondition(Comparator comparator, AddressPart addressPart, Match match, List<String> envelopeParts, List<String> keys) {
        super(addressPart, match, comparator);
        this.envelopeParts = envelopeParts;
        this.keys = keys;
    }
}
