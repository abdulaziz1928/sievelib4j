package com.abdulaziz;

import com.abdulaziz.builder.SieveBuilder;
import com.abdulaziz.builder.SieveFilterSet;
import com.abdulaziz.builder.actions.FileIntoAction;
import com.abdulaziz.builder.actions.VacationAction;
import com.abdulaziz.builder.conditions.AddressCondition;
import com.abdulaziz.builder.conditions.AndCondition;
import com.abdulaziz.builder.conditions.TrueCondition;
import com.abdulaziz.builder.control.ControlIf;
import com.abdulaziz.builder.types.AddressPart;
import com.abdulaziz.builder.types.MatchType;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
        var sieveFilter = new SieveFilterSet();
        sieveFilter.appendFilter(SieveBuilder.builder()
                .id(UUID.randomUUID())
                .ifStatement(ControlIf.builder()
                        .condition(
                                new AndCondition(
                                        new AddressCondition(MatchType.IS, AddressPart.LOCAL_PART, List.of("from\""), List.of("user1@home.org")),
                                        new TrueCondition()
                                ))
                        .actions(List.of(new FileIntoAction("Sent"),new VacationAction(1,"abc","user1@home.org",null,null,null,"abc")))
                        .build())
                .build());
        System.out.println(sieveFilter.generateScript());
    }
}
