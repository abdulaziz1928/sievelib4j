package com.abdulaziz1928.builder.conditions;

import com.abdulaziz1928.builder.SieveUtils;
import com.abdulaziz1928.builder.types.SizeType;
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
