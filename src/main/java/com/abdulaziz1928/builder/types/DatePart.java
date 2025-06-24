package com.abdulaziz1928.builder.types;

public enum DatePart {
    YEAR,
    MONTH,
    DAY,
    DATE,
    JULIAN,
    HOUR,
    MINUTE,
    SECOND,
    TIME,
    ISO8601,
    STD11,
    ZONE,
    WEEKDAY;

    public String getName() {
        return switch (this) {
            case YEAR -> "year";
            case MONTH -> "month";
            case DAY -> "day";
            case DATE -> "date";
            case JULIAN -> "julian";
            case HOUR -> "hour";
            case MINUTE -> "minute";
            case SECOND -> "second";
            case TIME -> "time";
            case ISO8601 -> "iso8601";
            case STD11 -> "std11";
            case ZONE -> "zone";
            case WEEKDAY -> "weekday";
        };
    }

}
