package com.abdulaziz1928.builder;

import com.abdulaziz1928.AbstractManageSieveTest;
import com.abdulaziz1928.builder.actions.BreakAction;
import com.abdulaziz1928.builder.actions.FileIntoAction;
import com.abdulaziz1928.builder.conditions.TrueCondition;
import com.abdulaziz1928.builder.control.ControlIf;
import com.fluffypeople.managesieve.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class SieveEnclosingBlockTest extends AbstractManageSieveTest {

    @Test
    void shouldBuildAndValidateForEachPartBlockSuccessfully() throws IOException, ParseException {
        var builder = SieveBuilder.builder()
                .id(UUID.randomUUID())
                .enclosingBlock(new ForEveryPartBlock())
                .ifStatement(ControlIf.builder()
                        .condition(new TrueCondition())
                        .actions(List.of(new FileIntoAction("Inbox"), new BreakAction()))
                        .build())
                .build();
        var filter = new SieveFilterSet();
        filter.appendFilter(builder);
        var script = filter.generateScript();
        var res = client.checkscript(script);

        Assertions.assertTrue(res.isOk(), getFailureMessage(script, res.getMessage()));
    }
}
