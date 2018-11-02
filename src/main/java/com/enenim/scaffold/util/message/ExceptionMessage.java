package com.enenim.scaffold.util.message;

import com.enenim.scaffold.constant.CommonConstant;
import com.enenim.scaffold.util.RequestUtil;

public class ExceptionMessage {
    private static final String FILE_NAME = "exception";

    public static String msg(String key) {
        return PropertyUtil.msg(key, RequestUtil.getLang(), FILE_NAME);
    }

    public static String msg(String key, String role) {
        return PropertyUtil.msg(key, RequestUtil.getLang(), FILE_NAME).replace(CommonConstant.PLACE_HOLDER, role);
    }
}