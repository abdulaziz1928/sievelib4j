package com.abdulaziz.builder.types;

public enum AddressPart {
    LOCAL_PART,
    DOMAIN,
    ALL,
    ;

    public String getSyntax() {
        return switch (this) {
            case LOCAL_PART -> ":localpart";
            case DOMAIN -> ":domain";
            case ALL -> ":all";
        };
    }
}
