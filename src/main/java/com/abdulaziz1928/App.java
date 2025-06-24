package com.abdulaziz1928;

import com.abdulaziz1928.builder.SieveBuilder;
import com.abdulaziz1928.builder.SieveFilterSet;
import com.abdulaziz1928.builder.actions.DiscardAction;
import com.abdulaziz1928.builder.actions.FileIntoAction;
import com.abdulaziz1928.builder.conditions.EnvelopeCondition;
import com.abdulaziz1928.builder.control.ControlElse;
import com.abdulaziz1928.builder.control.ControlIf;
import com.abdulaziz1928.builder.types.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class App {
    public static void main(String[] args) throws IOException {
        var script = new SieveFilterSet();

        script.appendFilter(SieveBuilder.builder()
                .id(UUID.randomUUID())
                .ifStatement(
                        ControlIf.builder()
                                .condition(new EnvelopeCondition(
                                        AddressPart.LOCAL_PART,
                                        Match.contains(),
                                        List.of("from"),
                                        List.of("john")
                                ))
                                .actions(List.of(new FileIntoAction("Friends")))
                                .build())
                .elseStatement(new ControlElse(List.of(new DiscardAction())))
                .build());

        System.out.println(script.generateScript());
    }
}
