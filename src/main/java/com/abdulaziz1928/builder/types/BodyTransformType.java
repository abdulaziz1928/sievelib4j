package com.abdulaziz1928.builder.types;

public enum BodyTransformType {
    RAW,
    CONTENT,
    TEXT;

    public String getSyntax() {
        return switch (this) {
            case RAW -> ":raw";
            case CONTENT -> ":content";
            case TEXT -> ":text";
        };
    }
}
