package com.enenim.scaffold.util;

import java.util.ArrayList;
import java.util.List;

public class ErrorUtil {
    public static List<String> getErrorTrace(StackTraceElement[] elements){
        List<String> errorTrace = new ArrayList<>();
        for(StackTraceElement element : elements){
            if(element.getClassName().contains(AppUtil.basePackage())){
                errorTrace.add("class: " + element.getClassName() + ", method: " + element.getMethodName() + ", Line number: " + element.getLineNumber());
            }
        }
        return errorTrace;
    }
}