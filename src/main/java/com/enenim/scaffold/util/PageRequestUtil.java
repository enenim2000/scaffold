package com.enenim.scaffold.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static com.enenim.scaffold.constant.CommonConstant.PAGE;
import static com.enenim.scaffold.constant.CommonConstant.PAGE_SIZE;
import static com.enenim.scaffold.constant.ModelFieldConstant.CREATEDAT;

public class PageRequestUtil {

    public static PageRequest getPageRequest() {
        return PageRequest.of(getPage(), PAGE_SIZE, Sort.Direction.DESC, CREATEDAT);
    }

    public static PageRequest getPageRequest(int pageSize) {
        return PageRequest.of(getPage(), pageSize, Sort.Direction.DESC, CREATEDAT);
    }

    public static PageRequest getPageRequest(int pageSize, Sort.Direction direction) {
        return PageRequest.of(getPage(), pageSize, direction, CREATEDAT);
    }

    public static PageRequest getPageRequest(int pageSize, Sort.Direction direction, String columnName) {
        return PageRequest.of(getPage(), pageSize, direction, columnName);
    }

    private static int getPage(){
        return Integer.valueOf(RequestUtil.getRequest().getParameter(PAGE) != null ? RequestUtil.getRequest().getParameter(PAGE) : "1") - 1;
    }

    public static String q(){
        return RequestUtil.getRequest().getParameter("getQ")==null?"":RequestUtil.getRequest().getParameter("getQ");
    }
}