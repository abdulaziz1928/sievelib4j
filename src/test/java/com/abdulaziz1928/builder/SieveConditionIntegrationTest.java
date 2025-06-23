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
                Arguments.of("address", new AddressCondition(MatchType.IS, AddressPart.ALL, List.of("from"), List.of("user@examle.com"))),
                Arguments.of("address 2", new AddressCondition(Comparator.OCTET, MatchType.MATCHES, AddressPart.DOMAIN, List.of("from"), List.of("examle.com"))),
                Arguments.of("address 3", new AddressCondition(null, null, List.of("from"), List.of("user@examle.com"))),
                Arguments.of("address 4", new AddressCondition(MatchType.IS, AddressPart.LOCAL_PART, List.of("from"), List.of("user"))),
                Arguments.of("envelope", new EnvelopeCondition(AddressPart.ALL, MatchType.IS, List.of("from"), List.of("user@examle.com"))),
                Arguments.of("envelope 2", new EnvelopeCondition(Comparator.OCTET, AddressPart.DOMAIN, MatchType.MATCHES, List.of("from"), List.of("examle.com"))),
                Arguments.of("envelope 3", new AddressCondition(null, null, List.of("from"), List.of("user@examle.com"))),
                Arguments.of("has flags", new HasFlagCondition(MatchType.IS, List.of("var1"), List.of("\\Seen"))),
                Arguments.of("has flags 2", new HasFlagCondition(Comparator.OCTET, MatchType.IS, List.of("var1"), List.of("\\Seen"))),
                Arguments.of("has flags 3", new HasFlagCondition(Comparator.ASCII_CASEMAP, MatchType.IS, List.of("\\Seen"))),
                Arguments.of("has flags 4", new HasFlagCondition(MatchType.IS, List.of("\\Seen", "\\Deleted"))),
                Arguments.of("has flags 5", new HasFlagCondition(null, List.of("\\Seen"))),
                Arguments.of("header", new HeaderCondition(MatchType.CONTAINS, List.of("From", "Cc"), List.of("example.com", "abc.com"))),
                Arguments.of("header 2", new HeaderCondition(Comparator.OCTET, MatchType.CONTAINS, List.of("From", "Cc"), List.of("example.com", "abc.com"))),
                Arguments.of("size", new SizeCondition(SizeType.UNDER, 1000)),
                Arguments.of("size 2", new SizeCondition(SizeType.OVER, 1000)),
                Arguments.of("body", new BodyCondition(Comparator.OCTET, MatchType.IS, BodyTransform.raw(), List.of("MAKE MONEY FAST"))),
                Arguments.of("body 2", new BodyCondition( MatchType.CONTAINS, BodyTransform.content(List.of("text")), List.of("missile", "coordinates"))),
                Arguments.of("body 3", new BodyCondition( MatchType.CONTAINS, BodyTransform.text(), List.of("project schedule")))
        );
    }
}