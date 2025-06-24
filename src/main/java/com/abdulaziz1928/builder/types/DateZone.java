package com.abdulaziz1928.builder.types;

import lombok.Getter;

import java.util.Objects;

@Getter
public final class DateZone {
    private final DateZoneType type;
    private final String zone;

    private DateZone(DateZoneType type, String zone) {
        this.type = Objects.requireNonNull(type, "zone type is required");
        this.zone = zone;
    }

    public static DateZone zone(String zone) {
        return new DateZone(DateZoneType.ZONE, zone);
    }

    public static DateZone original() {
        return new DateZone(DateZoneType.ORIGINAL_ZONE, null);
    }
}
