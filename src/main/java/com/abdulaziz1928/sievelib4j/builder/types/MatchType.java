package com.abdulaziz1928.sievelib4j.builder.types;

import com.abdulaziz1928.sievelib4j.builder.SieveImports;

public enum MatchType {
    CONTAINS,
    IS,
    MATCHES,
    COUNT,
    VALUE,
    REGEX;

    public String getSyntax() {
        return switch (this) {
            case CONTAINS -> ":contains";
            case IS -> ":is";
            case MATCHES -> ":matches";
            case COUNT -> ":count";
            case VALUE -> ":value";
            case REGEX -> ":regex";
        };
    }

    public String getRequiredExtension() {
        return switch (this) {
            case COUNT, VALUE -> SieveImports.Conditions.RELATIONAL;
            case REGEX -> SieveImports.Conditions.REGEX;
            default -> null;
        };
    }
}
