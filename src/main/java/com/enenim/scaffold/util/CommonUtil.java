package com.enenim.scaffold.util;

import java.util.concurrent.ThreadLocalRandom;

public class CommonUtil {
    public static String getCode(){
        StringBuilder code = new StringBuilder();

        ThreadLocalRandom.current()
                .ints(0, 100)
                .distinct()
                .limit(6).forEach((value) -> code.append(String.valueOf(value)));

        return code.toString();
    }
}
