package com.enenim.scaffold.util.message;

import com.enenim.scaffold.util.RequestUtil;

public class ExceptionMessage {
    private static final String FILE_NAME = "exception";

    public static String msg(String key) {
        return PropertyUtil.msg(key, RequestUtil.getLang(), FILE_NAME);
    }
}

