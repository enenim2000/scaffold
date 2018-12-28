package com.enenim.scaffold.util.message;

import com.enenim.scaffold.util.RequestUtil;

public class CommonMessage {
    private static final String FILE_NAME = "messages";

    public static String msg(String key) {
        return PropertyUtil.msg(key, RequestUtil.getLang(), FILE_NAME);
    }
}
