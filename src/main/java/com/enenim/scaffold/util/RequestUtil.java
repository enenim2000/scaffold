package com.enenim.scaffold.util;

import com.enenim.scaffold.constant.CommonConstant;
import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.model.cache.LoginCache;
import com.enenim.scaffold.model.dao.Biller;
import com.enenim.scaffold.model.dao.Consumer;
import com.enenim.scaffold.model.dao.Staff;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.enenim.scaffold.constant.CommonConstant.*;

public class RequestUtil {

    public static HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     *
     * @return IP Address getFrom baseRequest; baseRequest.getIpAddress()
     */
    public static String getIpAddress(){
        return getRequest().getParameter(CommonConstant.IP_ADDRESS);
    }

    /**
     *
     * @return Browser getFrom baseRequest; baseRequest.getUserAgent()
     */
    public static String getUserAgent(){
        return getRequest().getParameter(CommonConstant.USER_AGENT);
    }

    public static Staff getStaff(){
        return (Staff) getRequest().getAttribute(RoleConstant.STAFF);
    }

    public static Biller getBiller(){
        return (Biller) getRequest().getAttribute(RoleConstant.BILLER);
    }

    public static Consumer getConsumer(){
        return (Consumer) getRequest().getAttribute(RoleConstant.CONSUMER);
    }

    /**
     *
     * @return route identifier or task, this should be set in the interceptor; request.setAttribute("task_route", handlerMethod.getMethodAnnotation(Permission.class).toString())
     */
    public static String getTaskRoute(){
        return (String) getRequest().getAttribute("task_route");
    }

    /**
     *
     * @return T data in Request<T>, request.setAttribute("request", concatenate(baseRequest.getData() + uri + datetime))//rid => is the concatenation of baseRequest, uri, datetime via AOP
     */
    public static String getRID(){
        return (String)getRequest().getAttribute("rid");
    }

    /**
     *
     * @return T data in Request<T>, request.setAttribute("request", baseRequest.getData())
     */
    public static Object getBaseRequestBody(){
        return getRequest().getAttribute("request");
    }

    public static String getQ(){
        return getRequest().getParameter(Q)==null?"":getRequest().getParameter(Q);
    }

    public static String getTo(){
        return getRequest().getParameter(TO)==null?"":getRequest().getParameter(TO);
    }

    public static String getFrom(){
        return getRequest().getParameter(FROM)==null?"":getRequest().getParameter(FROM);
    }

    public static String perPage(){
        return getRequest().getParameter(PER_PAGE)==null?"":getRequest().getParameter(PER_PAGE);
    }

    public static LoginCache getLoginToken(){
        return (LoginCache) getRequest().getAttribute("loginToken");
    }

    public static void setLoginToken(LoginCache loginToken){
        getRequest().setAttribute("loginToken", loginToken);
    }

    public static void setUserAgent(String userAgent){
        getRequest().setAttribute(CommonConstant.USER_AGENT, userAgent);
    }

    public static void setIpAdress(String ipAdress){
        getRequest().setAttribute(CommonConstant.IP_ADDRESS, ipAdress);
    }

    public static void setStaff(Object staff){
        getRequest().setAttribute(RoleConstant.STAFF, staff);
    }

    public static void setBiller(Object biller){
        getRequest().setAttribute(RoleConstant.BILLER, biller);
    }

    public static void setConsumer(Object consumer){
        getRequest().setAttribute(RoleConstant.CONSUMER, consumer);
    }

    public static void setRID(String rid){
        getRequest().setAttribute("rid", rid);
    }

    public static void setTaskRoute(String taskRoute){
        getRequest().setAttribute("task_route", taskRoute);
    }

    public static int getPage(){
        return Integer.valueOf(getRequest().getParameter(PAGE) != null ? getRequest().getParameter(PAGE) : "1");
    }

    public static void setMessage(String message){
        getRequest().setAttribute(MESSAGE, message);
    }

    public static String getMessage(){
        return (String) getRequest().getAttribute(MESSAGE);
    }

    public static void setLang(String lang){
        getRequest().setAttribute(LANG, lang);
    }

    public static String getLang(){
        return (String) getRequest().getAttribute(LANG);
    }
}