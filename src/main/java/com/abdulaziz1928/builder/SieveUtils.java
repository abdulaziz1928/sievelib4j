package com.abdulaziz1928.builder;

import com.abdulaziz1928.builder.exceptions.SieveRequiredParamException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SieveUtils {
    public static <T> T requiredParam(T param,String message){
        if (Objects.isNull(param))
            throw new SieveRequiredParamException(message);
        return param;
    }

    public static <T> List<T> requiredParamList(List<T> paramList,String message){
        if (Objects.isNull(paramList) || paramList.isEmpty())
            throw new SieveRequiredParamException(message);
        return paramList;
    }
}
