package com.enenim.scaffold.util;

import com.enenim.scaffold.constant.CommonConstant;
import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.enums.AuditStatus;
import com.enenim.scaffold.enums.AuthorizationStatus;
import com.enenim.scaffold.model.cache.LoginCache;
import com.enenim.scaffold.model.dao.*;
import com.enenim.scaffold.util.message.SpringMessage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.enenim.scaffold.constant.CommonConstant.*;

public class RequestUtil {

    public static HttpServletRequest getRequest(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return new ContentCachingRequestWrapper(request);
    }

    /**
     *
     * @return IP Address getFrom baseRequest; baseRequest.getIpAddress()
     */
    public static String getIpAddress(){
        return (String) getRequest().getAttribute(CommonConstant.IP_ADDRESS);
    }

    /**
     *
     * @return Browser getFrom baseRequest; baseRequest.getUserAgent()
     */
    public static String getUserAgent(){
        return (String) getRequest().getAttribute(CommonConstant.USER_AGENT);
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
    public static Object getRequestBody(){
        return getRequest().getAttribute("request");
    }

    public static void setRequestBody(Object request){
        getRequest().setAttribute("request", request);
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
        return StringUtils.isEmpty(getRequest().getParameter(PER_PAGE)) ? String.valueOf(PAGE_SIZE) :getRequest().getParameter(PER_PAGE);
    }

    public static LoginCache getLoginToken(){
        return (LoginCache) getRequest().getAttribute("loginToken");
    }

    public static Login getLogin(){
        return (Login) getRequest().getAttribute(CommonConstant.LOGIN);
    }

    public static void setLoginToken(LoginCache loginToken){
        getRequest().setAttribute("loginToken", loginToken);
    }

    public static void setLogin(Login login){
        getRequest().setAttribute(CommonConstant.LOGIN, login);
    }

    public static void setUserAgent(String userAgent){
        getRequest().setAttribute(CommonConstant.USER_AGENT, userAgent);
    }

    public static void setIpAdress(String ipAdress){
        getRequest().setAttribute(CommonConstant.IP_ADDRESS, ipAdress);
    }

    public static void setStaff(Staff staff){
        getRequest().setAttribute(RoleConstant.STAFF, staff);
    }

    public static void setBiller(Biller biller){
        getRequest().setAttribute(RoleConstant.BILLER, biller);
    }

    public static void setConsumer(Consumer consumer){
        getRequest().setAttribute(RoleConstant.CONSUMER, consumer);
    }

    public static void setRID(String rid){
        getRequest().setAttribute("rid", rid);
    }

    public static void setTaskRoute(String taskRoute){
        getRequest().setAttribute("task_route", taskRoute);
    }

    public static String getApiKey(){
        return getRequest().getHeader(CommonConstant.API_KEY);
    }

    public static void setChannel(PaymentChannel paymentChannel){
        getRequest().setAttribute(PAYMENT_CHANNEL, paymentChannel);
    }

    public static PaymentChannel getChannel(){
        return (PaymentChannel) getRequest().getAttribute(PAYMENT_CHANNEL);
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

    public static AuthorizationStatus getAuthorizationStatus(){
        return (AuthorizationStatus) getRequest().getAttribute(STATUS);
    }

    public static void setAuthorizationStatus(AuthorizationStatus status){
        getRequest().setAttribute(STATUS, status);
    }

    public static Map<String, Authorization> getAuthorizations(){
        if(StringUtils.isEmpty(getRequest().getAttribute(AUTHORIZATION))){
            return new HashMap<>();
        }else {
            return new ObjectMapper().convertValue(getRequest().getAttribute(AUTHORIZATION), new TypeReference<Map<String, Object>>(){});
        }
    }

    public static void setAuthorization(String entityName, Authorization authorization){
        Map<String, Authorization> authorizations = getAuthorizations();
        authorizations.put(entityName, authorization);
    }

    public static AuditStatus getAuditStatus(){
        return (AuditStatus) getRequest().getAttribute(AUDIT_STATUS);
    }

    public static void setAuditStatus(AuditStatus auditStatus){
        getRequest().setAttribute(AUDIT_STATUS, auditStatus);
    }

    public static void setUserAction(String userAction){
        getRequest().setAttribute("user_action", userAction);
    }

    public static String getUserAction(){
        return (String) getRequest().getAttribute("user_action");
    }

    public static String getLang(){
        if(StringUtils.isEmpty(getRequest().getAttribute(LANG))){
            return SpringMessage.msg(LANG);
        }
        return (String) getRequest().getAttribute(LANG);
    }
}