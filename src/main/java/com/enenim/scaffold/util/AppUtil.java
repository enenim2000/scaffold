package com.enenim.scaffold.util;

import com.enenim.scaffold.ScaffoldApplication;

class AppUtil {
    static String basePackage(){
        return ScaffoldApplication.class.getPackage().getName();
    }
}