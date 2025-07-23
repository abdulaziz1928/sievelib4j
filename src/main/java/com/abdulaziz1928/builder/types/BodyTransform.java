package com.abdulaziz1928.builder.types;

import com.abdulaziz1928.builder.SieveUtils;
import lombok.Getter;

import java.util.List;

@Getter
public final class BodyTransform {
    private final BodyTransformType type;
    private final List<String> contentList;

    private BodyTransform(BodyTransformType type, List<String> contentList) {
        this.type = SieveUtils.requiredParam(type, "body-transform type is required");
        this.contentList = contentList;
    }

    public static BodyTransform raw() {
        return new BodyTransform(BodyTransformType.RAW, null);
    }

    public static BodyTransform text() {
        return new BodyTransform(BodyTransformType.TEXT, null);
    }

    public static BodyTransform content(List<String> contentTypes) {
        return new BodyTransform(BodyTransformType.CONTENT, SieveUtils.requiredParamList(contentTypes, "content-type-list must not be empty"));
    }

}