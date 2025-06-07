package com.abdulaziz1928.builder.types;

public enum Comparator {
    OCTET,
    ASCII_CASEMAP;

    public String getName() {
        return switch (this) {
            case OCTET -> "i;octet";
            case ASCII_CASEMAP -> "i;ascii-casemap";
        };
    }
}
