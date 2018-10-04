package com.enenim.scaffold.util.message;

public class SettingMessage {
    private static final String FILE_NAME = "setting";

    public static String msg(String key) {
        return PropertyUtil.msg(key, FILE_NAME);
    }

    public static String msg(String key, String lang) {
        return PropertyUtil.msg(key, lang, FILE_NAME);
    }
}
