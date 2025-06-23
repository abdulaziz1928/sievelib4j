package com.abdulaziz1928.builder.types;

import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class BodyTransform {
    private final BodyTransformType type;
    private final List<String> contentList;

    private BodyTransform(BodyTransformType type, List<String> contentList) {
        this.type = Objects.requireNonNull(type,"body-transform type is required");
        this.contentList = contentList;
    }

    public static BodyTransform raw() {
        return new BodyTransform(BodyTransformType.RAW, null);
    }

    public static BodyTransform text() {
        return new BodyTransform(BodyTransformType.TEXT, null);
    }

    public static BodyTransform content(List<String> contentTypes) {
        if (Objects.isNull(contentTypes) || contentTypes.isEmpty())
            throw new IllegalArgumentException("content-type-list must not be empty");
        return new BodyTransform(BodyTransformType.CONTENT, contentTypes);
    }

}