package com.abdulaziz1928;

import com.abdulaziz1928.builder.SieveBuilder;
import com.abdulaziz1928.builder.SieveFilterSet;
import com.abdulaziz1928.builder.actions.FileIntoAction;
import com.abdulaziz1928.builder.actions.VacationAction;
import com.abdulaziz1928.builder.conditions.AddressCondition;
import com.abdulaziz1928.builder.conditions.AndCondition;
import com.abdulaziz1928.builder.conditions.TrueCondition;
import com.abdulaziz1928.builder.control.ControlIf;
import com.abdulaziz1928.builder.types.AddressPart;
import com.abdulaziz1928.builder.types.MatchType;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class App {
    public static void main(String[] args) throws IOException {
        var sieveFilter = new SieveFilterSet();
        sieveFilter.appendFilter(SieveBuilder.builder()
                .id(UUID.randomUUID())
                .ifStatement(ControlIf.builder()
                        .condition(
                                new AndCondition(
                                        new AddressCondition(MatchType.IS, AddressPart.DOMAIN, List.of("from"), List.of("home.org")),
                                        new TrueCondition()
                                ))
                        .actions(List.of(new FileIntoAction("Sent"),new VacationAction(1,"abc","user1@home.org",null,null,null,"abc")))
                        .build())
                .build());
        System.out.println(sieveFilter.generateScript());
    }
}
