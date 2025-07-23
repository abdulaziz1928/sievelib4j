package com.abdulaziz1928.builder.types;

import com.abdulaziz1928.builder.SieveUtils;
import lombok.Getter;


@Getter
public final class Match {
    private final MatchType matchType;
    private final RelationalMatch relationalMatch;

    private Match(MatchType matchType, RelationalMatch relationalMatch) {
        this.matchType = SieveUtils.requiredParam(matchType, "match type is required");
        this.relationalMatch = SieveUtils.requiredParam(relationalMatch, "relational match is required");
    }

    private Match(MatchType matchType) {
        this.matchType = SieveUtils.requiredParam(matchType, "match type is required");
        this.relationalMatch = null;
    }

    public static Match contains() {
        return new Match(MatchType.CONTAINS);
    }

    public static Match is() {
        return new Match(MatchType.IS);
    }

    public static Match matches() {
        return new Match(MatchType.MATCHES);
    }

    public static Match count(RelationalMatch relationalMatch) {
        return new Match(MatchType.COUNT, relationalMatch);
    }

    public static Match value(RelationalMatch relationalMatch) {
        return new Match(MatchType.VALUE, relationalMatch);
    }

    public static Match regex() {
        return new Match(MatchType.REGEX);
    }

    public String getSyntax() {
        var syntax = matchType.getSyntax();
        if (relationalMatch != null)
            syntax = syntax.concat(String.format(" \"%s\"", relationalMatch.getName()));
        return syntax;
    }
}
