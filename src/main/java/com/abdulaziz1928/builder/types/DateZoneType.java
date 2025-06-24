package com.abdulaziz1928.builder.types;

public enum DateZoneType {
    ZONE,
    ORIGINAL_ZONE;

    public String getSyntax() {
        return switch (this) {
            case ZONE -> ":zone";
            case ORIGINAL_ZONE -> ":originalzone";
        };
    }
}
