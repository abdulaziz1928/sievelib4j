package com.abdulaziz1928.builder.types;

import lombok.Getter;

@Getter
public final class Index {
    private final int idx;
    private final boolean last;

    public Index(int index, boolean last) {
        this.idx = index;
        this.last = last;
    }

    public Index(int index) {
        this(index, false);
    }
}
