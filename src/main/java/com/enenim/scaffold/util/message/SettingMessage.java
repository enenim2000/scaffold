package com.enenim.scaffold.util.message;

import com.enenim.scaffold.util.RequestUtil;

public class SettingMessage {
    private static final String FILE_NAME = "setting";

    public static String msg(String key) {
        return PropertyUtil.msg(key, RequestUtil.getLang(), FILE_NAME);
    }
}
