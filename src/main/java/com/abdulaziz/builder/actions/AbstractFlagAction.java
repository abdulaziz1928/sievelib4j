package com.abdulaziz.builder.actions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@AllArgsConstructor
@Getter
public class AbstractFlagAction extends SieveAction{
    List<String> flags;


}
