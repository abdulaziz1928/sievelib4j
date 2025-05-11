package com.abdulaziz.builder.types;

public enum SizeType {
    UNDER,
    OVER,
    ;

    public String getSyntax(){
        return switch (this){
            case OVER -> ":over";
            case UNDER -> ":under";
        };
    }
}
