package com.abdulaziz1928.builder;

import com.abdulaziz1928.AbstractManageSieveTest;
import com.abdulaziz1928.builder.actions.*;
import com.abdulaziz1928.builder.conditions.TrueCondition;
import com.abdulaziz1928.builder.control.ControlIf;
import com.fluffypeople.managesieve.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

class SieveActionIntegrationTest extends AbstractManageSieveTest {

    @ParameterizedTest(name = "Action: {0}")
    @MethodSource("actionsToTest")
    void shouldBuildAndValidateScriptConditionSuccessfully(String label, List<SieveAction> actions) throws IOException, ParseException {
        var builder = SieveBuilder.builder()
                .id(UUID.randomUUID())
                .ifStatement(ControlIf.builder()
                        .condition(new TrueCondition())
                        .actions(actions)
                        .build())
                .build();
        var filter = new SieveFilterSet();
        filter.appendFilter(builder);
        var script = filter.generateScript();
        var res = client.checkscript(script);

        Assertions.assertTrue(res.isOk(), getFailureMessage(script, res.getMessage()));
    }

    static Stream<Arguments> actionsToTest() {
        return Stream.of(
                Arguments.of("discard", List.of(new DiscardAction())),
                Arguments.of("redirect", List.of(new RedirectAction("user1@example.com"))),
                Arguments.of("redirect 2", List.of(new RedirectAction("user1@example.com", true))),
                Arguments.of("fileinto", List.of(new FileIntoAction("Inbox"))),
                Arguments.of("fileinto 2", List.of(new FileIntoAction("Inbox", true))),
                Arguments.of("fileinto 3", List.of(new FileIntoAction("Inbox", List.of("\\Seen")))),
                Arguments.of("fileinto 4", List.of(new FileIntoAction("Inbox", true, List.of("\\Seen")))),
                Arguments.of("keep", List.of(new KeepAction())),
                Arguments.of("keep", List.of(new KeepAction(List.of("\\Flagged")))),
                Arguments.of("setflags keep", List.of(
                        new SetFlagAction("myVar", List.of("\\Seen")),
                        new KeepAction("myVar"))),
                Arguments.of("setflags fileinto", List.of(
                        new SetFlagAction("myVar", List.of("\\Seen")),
                        new FileIntoAction("Inbox", true, "myVar"))),
                Arguments.of("setflags", List.of(new SetFlagAction("myVar", List.of("\\Seen")))),
                Arguments.of("setflags 2", List.of(new SetFlagAction(List.of("\\Seen")))),
                Arguments.of("addflags", List.of(new AddFlagAction("myVar", List.of("\\Seen")))),
                Arguments.of("addflags 2", List.of(new AddFlagAction(List.of("\\Seen")))),
                Arguments.of("removeflags", List.of(new RemoveFlagAction("myVar", List.of("\\Seen")))),
                Arguments.of("removeflags 2", List.of(new RemoveFlagAction(List.of("\\Seen")))),
                Arguments.of("vacation", List.of(new VacationAction(
                        1, "Out of office", "user1@example.com", List.of("user2@example.com"), null, "user2-handle", "im out of office"
                ))),
                Arguments.of("vacation 2", List.of(new VacationAction(
                        3, "Out of office", "user1@example.com", List.of("user2@example.com"), getMimeMessage(), "user2-handle", null
                ))),
                Arguments.of("vacation 3", List.of(new VacationAction(
                        null, null, null, null, null, null, "im out of office"
                ))),
                Arguments.of("stop",List.of(new StopAction()))
        );
    }

}
