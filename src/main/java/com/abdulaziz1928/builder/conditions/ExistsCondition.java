package com.abdulaziz1928.builder.conditions;

import com.abdulaziz1928.builder.SieveUtils;
import lombok.Getter;

import java.util.List;

@Getter
public class ExistsCondition extends SieveCondition {
    private final Boolean mime;
    private final Boolean anyChild;
    private final List<String> headers;

    public ExistsCondition(List<String> headers) {
        this(null, null, headers);
    }

    public ExistsCondition(Boolean mime, Boolean anyChild, List<String> headers) {
        this.mime = mime;
        this.anyChild = anyChild;
        this.headers = SieveUtils.requiredParamList(headers, "header-list is required");
    }
}
