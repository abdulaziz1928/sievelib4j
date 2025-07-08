package com.abdulaziz1928.builder.types;

public enum MimeOptsType {
    TYPE,
    SUB_TYPE,
    CONTENT_TYPE,
    PARAM;

    public String getSyntax() {
        return switch (this) {
            case TYPE -> ":type";
            case SUB_TYPE -> ":subtype";
            case CONTENT_TYPE -> ":contenttype";
            case PARAM -> ":param";
        };
    }
}
