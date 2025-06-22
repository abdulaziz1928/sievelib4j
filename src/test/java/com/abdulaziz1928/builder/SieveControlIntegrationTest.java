package com.abdulaziz1928.builder;

import com.abdulaziz1928.AbstractManageSieveTest;
import com.abdulaziz1928.builder.actions.DiscardAction;
import com.abdulaziz1928.builder.actions.FileIntoAction;
import com.abdulaziz1928.builder.actions.KeepAction;
import com.abdulaziz1928.builder.conditions.*;
import com.abdulaziz1928.builder.control.ControlElse;
import com.abdulaziz1928.builder.control.ControlElseIf;
import com.abdulaziz1928.builder.control.ControlIf;
import com.fluffypeople.managesieve.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

class SieveControlIntegrationTest extends AbstractManageSieveTest {

    @ParameterizedTest(name = "Control: {0}")
    @MethodSource("conditionsToTest")
    void shouldBuildAndValidateScriptConditionSuccessfully(String label, SieveBuilder builder) throws IOException, ParseException {
        var filter = new SieveFilterSet();
        filter.appendFilter(builder);
        var script = filter.generateScript();
        var res = client.checkscript(script);
        Assertions.assertTrue(res.isOk(), getFailureMessage(script, res.getMessage()));
    }

    static Stream<Arguments> conditionsToTest() {
        return Stream.of(
                Arguments.of("if", SieveBuilder.builder()
                        .ifStatement(new ControlIf(new TrueCondition(), List.of(new FileIntoAction("Inbox"))))
                        .build()
                ),
                Arguments.of("if elsif", SieveBuilder.builder()
                        .ifStatement(new ControlIf(new TrueCondition(), List.of(new FileIntoAction("Inbox"))))
                        .elseIfStatements(List.of(
                                new ControlElseIf(new TrueCondition(), List.of(new DiscardAction()))
                        ))
                        .build()
                ),
                Arguments.of("if elsif elsif", SieveBuilder.builder()
                        .ifStatement(new ControlIf(new TrueCondition(), List.of(new FileIntoAction("Inbox"))))
                        .elseIfStatements(List.of(
                                new ControlElseIf(new TrueCondition(), List.of(new DiscardAction())),
                                new ControlElseIf(new FalseCondition(), List.of(new KeepAction()))

                        ))
                        .build()
                ),
                Arguments.of("if else", SieveBuilder.builder()
                        .ifStatement(new ControlIf(new TrueCondition(), List.of(new FileIntoAction("Inbox"))))
                        .elseStatement(new ControlElse(List.of(new DiscardAction())
                        ))
                        .build()
                ),
                Arguments.of("if elsif else", SieveBuilder.builder()
                        .ifStatement(new ControlIf(new TrueCondition(), List.of(new FileIntoAction("Inbox"))))
                        .elseIfStatements(List.of(new ControlElseIf(new TrueCondition(), List.of(new KeepAction()))))
                        .elseStatement(new ControlElse(List.of(new DiscardAction())))
                        .build()
                )
        );
    }
}