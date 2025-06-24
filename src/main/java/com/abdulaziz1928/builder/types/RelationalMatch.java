package com.abdulaziz1928.builder.types;

public enum RelationalMatch {
    GT,
    GE,
    LT,
    LE,
    EQ,
    NE;

    public String getName() {
        return switch (this) {
            case GT -> "gt";
            case GE -> "ge";
            case LT -> "lt";
            case LE -> "le";
            case EQ -> "eq";
            case NE -> "ne";
        };
    }
}
