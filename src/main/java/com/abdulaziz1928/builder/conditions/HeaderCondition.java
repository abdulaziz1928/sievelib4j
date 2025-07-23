package com.abdulaziz1928.builder.conditions;

import com.abdulaziz1928.builder.SieveUtils;
import com.abdulaziz1928.builder.types.Comparator;
import com.abdulaziz1928.builder.types.Index;
import com.abdulaziz1928.builder.types.Match;
import com.abdulaziz1928.builder.types.MimeOpts;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class HeaderCondition extends SieveCondition {
    private final Comparator comparator;
    private final Match match;
    private final List<String> headers;
    private final List<String> keys;
    private final Index index;
    private final Boolean mime;
    private final Boolean anyChild;
    private final MimeOpts mimeOpts;


    public HeaderCondition(Boolean mime, Boolean anyChild, MimeOpts mimeOpts, Index index, Comparator comparator, Match match, List<String> headers, List<String> keys) {
        this.index = index;
        this.comparator = comparator;
        this.match = match;
        this.headers = SieveUtils.requiredParamList(headers, "header-list is required");
        this.keys = SieveUtils.requiredParamList(keys, "key-list is required");
        this.mime = mime;
        this.anyChild = anyChild;
        this.mimeOpts = mimeOpts;
    }

    public HeaderCondition(Boolean mime, Boolean anyChild, MimeOpts mimeOpts, Comparator comparator, Match match, List<String> headers, List<String> keys) {
        this(mime, anyChild, mimeOpts, null, comparator, match, headers, keys);
    }


    public HeaderCondition(Comparator comparator, Match match, List<String> headers, List<String> keys) {
        this(null, null, null, null, comparator, match, headers, keys);
    }

    public HeaderCondition(Match match, List<String> headers, List<String> keys) {
        this(null, match, headers, keys);
    }

    public HeaderCondition withIndex(Index index) {
        return Objects.equals(this.index, index) ? this : new HeaderCondition(getMime(), getAnyChild(), getMimeOpts(), index, getComparator(), getMatch(), getHeaders(), getKeys());
    }
}
