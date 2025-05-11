package com.abdulaziz1928.builder.conditions;

import com.abdulaziz1928.builder.types.SizeType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SizeCondition extends SieveCondition {
    private final SizeType sizeType;
    private final long size;
}
