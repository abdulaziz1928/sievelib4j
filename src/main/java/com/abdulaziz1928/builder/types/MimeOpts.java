package com.abdulaziz1928.builder.types;

import com.abdulaziz1928.builder.SieveUtils;
import lombok.Getter;

import java.util.List;

@Getter
public class MimeOpts {
    private final MimeOptsType type;
    private final List<String> paramList;

    private MimeOpts(MimeOptsType type, List<String> paramList) {
        this.type = SieveUtils.requiredParam(type, "mime-opts type is required");
        this.paramList = paramList;
    }

    public static MimeOpts type() {
        return new MimeOpts(MimeOptsType.TYPE, null);
    }

    public static MimeOpts subType() {
        return new MimeOpts(MimeOptsType.SUB_TYPE, null);
    }

    public static MimeOpts contentType() {
        return new MimeOpts(MimeOptsType.CONTENT_TYPE, null);
    }

    public static MimeOpts param(List<String> paramList) {
        return new MimeOpts(MimeOptsType.PARAM, SieveUtils.requiredParamList(paramList,"param-list must not be empty"));
    }

}
