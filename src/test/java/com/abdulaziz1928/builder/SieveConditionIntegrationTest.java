package com.abdulaziz1928.builder;

import com.abdulaziz1928.AbstractManageSieveTest;
import com.abdulaziz1928.builder.actions.FileIntoAction;
import com.abdulaziz1928.builder.conditions.*;
import com.abdulaziz1928.builder.control.ControlIf;
import com.abdulaziz1928.builder.types.*;
import com.fluffypeople.managesieve.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class SieveConditionIntegrationTest extends AbstractManageSieveTest {

    @ParameterizedTest(name = "Condition: {0}")
    @MethodSource("conditionsToTest")
    void shouldBuildAndValidateScriptConditionSuccessfully(String label, SieveCondition condition) throws IOException, ParseException {
        var builder = SieveBuilder.builder()
                .id(UUID.randomUUID())
                .ifStatement(ControlIf.builder()
                        .condition(condition)
                        .actions(List.of(new FileIntoAction("Inbox")))
                        .build())
                .build();
        var filter = new SieveFilterSet();
        filter.appendFilter(builder);
        var script = filter.generateScript();
        var res = client.checkscript(script);

        Assertions.assertTrue(res.isOk(), getFailureMessage(script, res.getMessage()));
    }

    static Stream<Arguments> conditionsToTest() {
        return Stream.of(
                Arguments.of("true", new TrueCondition()),
                Arguments.of("false", new FalseCondition()),
                Arguments.of("not", new NotCondition(new FalseCondition())),
                Arguments.of("exists", new ExistsCondition(List.of("Cc", "Message-Id"))),
                Arguments.of("and", new AndCondition(new TrueCondition(), new FalseCondition())),
                Arguments.of("or", new OrCondition(new TrueCondition(), new FalseCondition())),
                Arguments.of("nested and or", new AndCondition(new TrueCondition(), new OrCondition(new TrueCondition(), new FalseCondition()))),
                Arguments.of("nested or and", new OrCondition(new FalseCondition(), new AndCondition(new TrueCondition(), new FalseCondition()))),
                Arguments.of("address", new AddressCondition(Match.is(), AddressPart.ALL, List.of("from"), List.of("user@examle.com"))),
                Arguments.of("address 2", new AddressCondition(Comparator.OCTET, Match.matches(), AddressPart.DOMAIN, List.of("from"), List.of("examle.com"))),
                Arguments.of("address 3", new AddressCondition(null, null, List.of("from"), List.of("user@examle.com"))),
                Arguments.of("address 4", new AddressCondition(Match.is(), AddressPart.LOCAL_PART, List.of("from"), List.of("user"))),
                Arguments.of("address 5", new AddressCondition(Match.is(), AddressPart.LOCAL_PART, List.of("from"), List.of("user")).withIndex(new Index(4))),
                Arguments.of("envelope", new EnvelopeCondition(AddressPart.ALL, Match.is(), List.of("from"), List.of("user@examle.com"))),
                Arguments.of("envelope 2", new EnvelopeCondition(Comparator.OCTET, AddressPart.DOMAIN, Match.matches(), List.of("from"), List.of("examle.com"))),
                Arguments.of("envelope 3", new AddressCondition(null, null, List.of("from"), List.of("user@examle.com"))),
                Arguments.of("has flags", new HasFlagCondition(Match.is(), List.of("var1"), List.of("\\Seen"))),
                Arguments.of("has flags 2", new HasFlagCondition(Comparator.OCTET, Match.is(), List.of("var1"), List.of("\\Seen"))),
                Arguments.of("has flags 3", new HasFlagCondition(Comparator.ASCII_CASEMAP, Match.is(), List.of("\\Seen"))),
                Arguments.of("has flags 4", new HasFlagCondition(Match.is(), List.of("\\Seen", "\\Deleted"))),
                Arguments.of("has flags 5", new HasFlagCondition(null, List.of("\\Seen"))),
                Arguments.of("header", new HeaderCondition(Match.contains(), List.of("From", "Cc"), List.of("example.com", "abc.com"))),
                Arguments.of("header 2", new HeaderCondition(Comparator.OCTET, Match.contains(), List.of("From", "Cc"), List.of("example.com", "abc.com"))),
                Arguments.of("header 3", new HeaderCondition(Comparator.OCTET, Match.contains(), List.of("From", "Cc"), List.of("example.com", "abc.com")).withIndex(new Index(2))),
                Arguments.of("size", new SizeCondition(SizeType.UNDER, 1000)),
                Arguments.of("size 2", new SizeCondition(SizeType.OVER, 1000)),
                Arguments.of("body", new BodyCondition(Comparator.OCTET, Match.is(), BodyTransform.raw(), List.of("MAKE MONEY FAST"))),
                Arguments.of("body 2", new BodyCondition(Match.contains(), BodyTransform.content(List.of("text")), List.of("missile", "coordinates"))),
                Arguments.of("body 3", new BodyCondition(Match.contains(), BodyTransform.text(), List.of("project schedule"))),
                Arguments.of("date ", new DateCondition(Comparator.OCTET, DateZone.original(), Match.is(), "date", DatePart.HOUR, List.of("09"))),
                Arguments.of("date 2", new DateCondition(Comparator.OCTET, DateZone.original(), Match.value(RelationalMatch.GE), "date", DatePart.HOUR, List.of("09")).withIndex(new Index(1, true))),
                Arguments.of("date 3", new DateCondition(DateZone.zone("+0000"), Match.value(RelationalMatch.GE), "date", DatePart.HOUR, List.of("09"))),
                Arguments.of("date 4", new DateCondition(null, null, "date", DatePart.MINUTE, List.of("09"))),
                Arguments.of("currentdate", new CurrentDateCondition(Comparator.OCTET, "+0000", Match.value(RelationalMatch.GE), DatePart.WEEKDAY, List.of("0"))),
                Arguments.of("currentdate 2", new CurrentDateCondition("+0000", Match.value(RelationalMatch.GE), DatePart.WEEKDAY, List.of("0"))),
                Arguments.of("currentdate 3", new CurrentDateCondition(null, null, DatePart.WEEKDAY, List.of("0")))

        );
    }
}