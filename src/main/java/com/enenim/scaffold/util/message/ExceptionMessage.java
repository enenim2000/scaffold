package com.enenim.scaffold.util.message;

public class ExceptionMessage {
    private static final String FILE_NAME = "exception";

    public static String msg(String key) {
        return PropertyUtil.msg(key, FILE_NAME);
    }

    public static String msg(String key, String lang) {
        return PropertyUtil.msg(key, lang, FILE_NAME);
    }
}
