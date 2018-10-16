package com.enenim.scaffold.util;

import java.util.concurrent.ThreadLocalRandom;

public class CommonUtil {
    public static String getCode(){
        return ThreadLocalRandom.current()
                .ints(0, 100)
                .distinct()
                .limit(6).toString();
    }
}
