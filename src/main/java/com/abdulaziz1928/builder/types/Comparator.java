package com.abdulaziz1928.builder.types;

public enum Comparator {
    OCTET,
    ASCII_CASEMAP;

    public String getComparator() {
        return switch (this) {
            case OCTET -> "i;octet";
            case ASCII_CASEMAP -> "i;ascii-casemap";
        };
    }
}
