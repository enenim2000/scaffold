package com.enenim.scaffold.util;

import com.enenim.scaffold.ScaffoldApplication;

public class AppUtil {
    public static String basePackage(){
        return ScaffoldApplication.class.getPackage().getName();
    }
}