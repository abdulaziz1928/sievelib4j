package com.abdulaziz1928.builder.types;

public enum MatchType {
    CONTAINS,
    IS,
    MATCHES,
    COUNT,
    VALUE;
    public String getSyntax() {
        return switch (this) {
            case CONTAINS -> ":contains";
            case IS -> ":is";
            case MATCHES -> ":matches";
            case COUNT -> ":count";
            case VALUE -> ":value";
        };
    }
}
