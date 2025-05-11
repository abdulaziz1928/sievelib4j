package com.abdulaziz1928.builder.types;

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
