package com.abdulaziz1928.builder.conditions;

import com.abdulaziz1928.builder.types.SizeType;
import lombok.Getter;

import java.util.Objects;

@Getter
public class SizeCondition extends SieveCondition {
    private final SizeType sizeType;
    private final long size;

    public SizeCondition(SizeType sizeType, long size) {
        this.sizeType = Objects.requireNonNull(sizeType,"size type is required");
        this.size = size;
    }
}
