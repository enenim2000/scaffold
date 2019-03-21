package com.enenim.scaffold.util;

import com.enenim.scaffold.constant.CommonConstant;
import com.enenim.scaffold.enums.AuditStatus;
import com.enenim.scaffold.model.cache.LoginCache;
import com.enenim.scaffold.model.dao.*;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.enenim.scaffold.constant.CommonConstant.*;

public class RequestUtil {

    private static String rid;
    private static String lang;
    private static Login login;
    private static Staff staff;
    private static Vendor vendor;
    private static PaymentChannel channel;
    private static String message;
    private static String taskRoute;
    private static Consumer consumer;
    private static String ipAddress;
    private static String userAgent;
    private static String userAction;
    private static String requestBody;
    private static LoginCache loginToken;
    private static AuditStatus auditStatus;
    private static Authorization authorization;

    public static HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     *
     * @return IP Address getFrom baseRequest; baseRequest.getIpAddress()
     */
    public static String getIpAddress(){
        return ipAddress;
    }

    /**
     *
     * @return Browser getFrom baseRequest; baseRequest.getUserAgent()
     */
    public static String getUserAgent(){
        return userAgent;
    }

    public static Staff getStaff(){
        return staff;
    }

    public static Vendor getVendor(){
        return vendor;
    }

    public static Consumer getConsumer(){
        return consumer;
    }

    /**
     *
     * @return route identifier or task, this should be set in the interceptor; request.setAttribute("task_route", handlerMethod.getMethodAnnotation(Permission.class).toString())
     */
    public static String getTaskRoute(){
        return taskRoute;
    }

    /**
     *
     * @return T data in Request<T>, request.setAttribute("request", concatenate(baseRequest.getData() + uri + datetime))//rid => is the concatenation of baseRequest, uri, datetime via AOP
     */
    public static String getRID(){
        return rid;
    }

    /**
     *
     * @return T data in Request<T>, request.setAttribute("request", baseRequest.getData())
     */
    public static Object getRequestBody(){
        return requestBody;
    }

    public static void setRequestBody(Object request){
        requestBody = JsonConverter.getJsonRecursive(request);
    }

    public static String getQ(){
        return getRequest().getParameter(Q)==null?"":getRequest().getParameter(Q);
    }

    /**
     *
     * @return end date
     */
    public static String getTo(){
        return getRequest().getParameter(TO)==null?"":getRequest().getParameter(TO);
    }

    /**
     *
     * @return start date
     */
    public static String getFrom(){
        return getRequest().getParameter(FROM)==null?"":getRequest().getParameter(FROM);
    }

    public static String perPage(){
        return StringUtils.isEmpty(getRequest().getParameter(PER_PAGE)) ? String.valueOf(PAGE_SIZE) :getRequest().getParameter(PER_PAGE);
    }

    public static LoginCache getLoginToken(){
        return loginToken;
    }

    public static Login getLogin(){
        return login;
    }

    public static void setLoginToken(LoginCache _loginToken){
        loginToken = _loginToken;
    }

    public static void setLogin(Login _login){
       login = _login;
    }

    public static void setUserAgent(String _userAgent){
        userAgent = _userAgent;
    }

    public static void setIpAdress(String _ipAdress){
        ipAddress = _ipAdress;
    }

    public static void setStaff(Staff _staff){
        staff = _staff;
    }

    public static void setVendor(Vendor _vendor){
        vendor = _vendor;
    }

    public static void setConsumer(Consumer _consumer){
        consumer = _consumer;
    }

    public static void setRID(String _rid){
        rid = _rid;
    }

    public static void setTaskRoute(String _taskRoute){
        taskRoute = _taskRoute;
    }

    public static String getApiKey(){
        return getRequest().getHeader(API_KEY);
    }

    public static void setChannel(PaymentChannel paymentChannel){
        channel = paymentChannel;
    }

    public static PaymentChannel getChannel(){
        return channel;
    }

    public static int getPage(){
        return Integer.valueOf(getRequest().getParameter(PAGE) != null ? getRequest().getParameter(PAGE) : "1");
    }

    public static void setMessage(String _message){
        message = _message;
    }

    public static String getMessage(){
        return message;
    }

    public static void setLang(String _lang){
        lang = _lang;
    }

    public static Authorization getAuthorization(){
       return authorization;
    }

    public static void setAuthorization(Authorization _authorization){
        authorization = _authorization;
    }

    public static AuditStatus getAuditStatus(){
        return auditStatus;
    }

    public static void setAuditStatus(AuditStatus _auditStatus){
        auditStatus = _auditStatus;
    }

    public static void setUserAction(String _userAction){
        userAction = _userAction;
    }

    public static String getUserAction(){
        return userAction;
    }

    public static void setConsumerSettings(HashMap<String, ConsumerSetting> consumerSettings){
        getRequest().setAttribute("consumer_settings", consumerSettings);
    }

    public static HashMap<String, ConsumerSetting> getConsumerSettings(){
        return (HashMap<String, ConsumerSetting>)getRequest().getAttribute("consumer_settings");
    }

    public static String getLang(){
        return lang;
    }

    public static void setCommonRequestProperties(Object request){
        String ipAddress;
        String userAgent;

        Map<String, Object> requestMap = CommonUtil.toMap(request);

        if(requestMap.containsKey(CommonConstant.IP_ADDRESS)){
            ipAddress = (String) requestMap.get(CommonConstant.IP_ADDRESS);
        }else {
            ipAddress = RequestUtil.getRequest().getParameter(CommonConstant.IP_ADDRESS);
        }

        if(requestMap.containsKey(CommonConstant.USER_AGENT)){
            userAgent = (String) requestMap.get(CommonConstant.USER_AGENT);
        }else {
            userAgent = RequestUtil.getRequest().getParameter(CommonConstant.USER_AGENT);
        }

        RequestUtil.setRequestBody(requestMap.get("data"));

        RequestUtil.setUserAgent(userAgent);
        RequestUtil.setIpAdress(ipAddress);
        RequestUtil.setRID(Security.encypt(getRequest().getRequestURI() + getRequest().getMethod() + getRequestBody()));

    }
}