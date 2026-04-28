package com.abdulaziz1928.sievelib4j.builder.conditions;

import com.abdulaziz1928.sievelib4j.builder.SieveUtils;
import com.abdulaziz1928.sievelib4j.builder.types.SizeType;
import lombok.Getter;


@Getter
public class SizeCondition extends SieveCondition {
    private final SizeType sizeType;
    private final long size;

    public SizeCondition(SizeType sizeType, long size) {
        this.sizeType = SieveUtils.requiredParam(sizeType, "size type is required");
        this.size = size;
    }
}
