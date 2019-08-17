package com.enenim.scaffold.util;

import com.enenim.scaffold.constant.CommonConstant;
import com.enenim.scaffold.enums.AuditStatus;
import com.enenim.scaffold.model.cache.LoginCache;
import com.enenim.scaffold.model.dao.*;
import com.enenim.scaffold.util.message.SpringMessage;
import com.enenim.scaffold.util.setting.ConsumerSystemSetting;
import com.enenim.scaffold.util.setting.VendorSystemSetting;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.enenim.scaffold.constant.CommonConstant.*;

public class RequestUtil {

    private static Map<String, RequestCache> REQUEST_CACHE = new HashMap<>();

    private static String getSessionId(){
        if(!StringUtils.isEmpty(getRequest())){
            return (String) getRequest().getAttribute("session_id");
        }
        return "";
    }

    public static void setSessionId(String sessionId){
        if(!StringUtils.isEmpty(getRequest())){
            getRequest().setAttribute("session_id", sessionId);
        }
    }

    public static void setRequestCache(RequestCache requestCache){
        REQUEST_CACHE.put(getSessionId(), requestCache);
    }

    public static RequestCache getRequestCache(){
        return StringUtils.isEmpty(REQUEST_CACHE.get(getSessionId())) ? new RequestCache() : REQUEST_CACHE.get(getSessionId());
    }

    public static HttpServletRequest getRequest(){
        return  ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
    }

    public static void removeRequestCache(){
        REQUEST_CACHE.remove(getSessionId());
    }

    /**
     *
     * @return IP Address getFrom baseRequest; baseRequest.getIpAddress()
     */
    public static String getIpAddress(){
        return (String) getRequest().getAttribute("ip_address");
    }

    /**
     *
     * @return Browser getFrom baseRequest; baseRequest.getUserAgent()
     */
    public static String getUserAgent(){
        return (String) getRequest().getAttribute("user_agent");
    }

    public static Staff getStaff(){
        return getRequestCache().getStaff();
    }

    public static Vendor getVendor(){
        return getRequestCache().getVendor();
    }

    public static Consumer getConsumer(){
        return getRequestCache().getConsumer();
    }

    /**
     *
     * @return route identifier or task, this should be set in the interceptor; request.setAttribute("task_route", handlerMethod.getMethodAnnotation(Permission.class).toString())
     */
    public static String getTaskRoute(){
        return getRequestCache().getTaskRoute();
    }

    /**
     *
     * @return T data in Request<T>, request.setAttribute("request", concatenate(baseRequest.getData() + uri + datetime))//rid => is the concatenation of baseRequest, uri, datetime via AOP
     */
    public static String getRID(){
        return getRequestCache().getRid();
    }

    /**
     *
     * @return T data in Request<T>, request.setAttribute("request", baseRequest.getData())
     */
    private static Object getRequestBody(){
        return getRequestCache().getRequestBody();
    }

    private static void setRequestBody(Object request){
        getRequestCache().setRequestBody(JsonConverter.getJsonRecursive(request));
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
        return getRequestCache().getLoginToken();
    }

    public static Login getLogin(){
        return getRequestCache().getLogin();
    }

    public static String getFullname(){
        return getRequestCache().getFullname();
    }

    public static String getApiKey(){
        return "7c68D454a3F8637DECE4f6E74Ey57By9CdDB130AFDEd7di89pDQ8e08CD14062c43A4d879f5693e719yd7aA703dd95B87ccBe4781A2F94EF4D87Ca7AB3B9d3032C631AC366779661101e96679g7dDDF83882200887C48884C27821c89BCBDF0D747D";//getRequest().getHeader(API_KEY);
    }

    public static PaymentChannel getChannel(){
        return getRequestCache().getChannel();
    }

    public static int getPage(){
        return Integer.valueOf(getRequest().getParameter(PAGE) != null ? getRequest().getParameter(PAGE) : "1");
    }

    public static String getMessage(){
        String message = (String) getRequest().getAttribute("message");
        return message == null ? "Processed successfully" : message;
    }

    public static void setLoginToken(LoginCache loginToken){
        getRequestCache().setLoginToken(loginToken);
    }

    public static void setLogin(Login login){
        getRequestCache().setLogin(login);
    }

    private static void setUserAgent(String userAgent){
        getRequest().setAttribute("user_agent", userAgent);
    }

    private static void setIpAdress(String ipAdress){
        getRequest().setAttribute("ip_address", ipAdress);
    }

    public static void setStaff(Staff staff){
        getRequestCache().setStaff(staff);
    }

    public static void setVendor(Vendor vendor){
        getRequestCache().setVendor(vendor);
    }

    public static void setFullname(String fullname){
        getRequestCache().setFullname(fullname);
    }

    public static void setConsumer(Consumer consumer){
        getRequestCache().setConsumer(consumer);
    }

    private static void setRID(String rid){
        getRequestCache().setRid(rid);
    }

    public static void setTaskRoute(String taskRoute){
        getRequestCache().setTaskRoute(taskRoute);
    }

    public static void setChannel(PaymentChannel paymentChannel){
        getRequestCache().setChannel(paymentChannel);
    }

    public static void setMessage(String message){
        getRequest().setAttribute("message", message);
    }

    public static void setLang(String lang){
        getRequestCache().setLang(lang);
    }

    public static Authorization getAuthorization(){
        return getRequestCache().getAuthorization();
    }

    public static void setAuthorization(Authorization authorization){
        getRequestCache().setAuthorization(authorization);
    }

    public static AuditStatus getAuditStatus(){
        return getRequestCache().getAuditStatus();
    }

    public static void setAuditStatus(AuditStatus auditStatus){
        getRequestCache().setAuditStatus(auditStatus);
    }

    public static void setUserAction(String userAction){
        getRequestCache().setUserAction(userAction);
    }

    public static String getUserAction(){
        return getRequestCache().getUserAction();
    }

    public static void setConsumerSystemSettings(HashMap<String, ConsumerSystemSetting> ConsumerSystemSettings){
        getRequestCache().setConsumerSystemSettings(ConsumerSystemSettings);
    }

    public static HashMap<String, ConsumerSystemSetting> getConsumerSystemSettings(){
        return getRequestCache().getConsumerSystemSettings();
    }

    public static void setVendorSystemSettings(HashMap<String, VendorSystemSetting> vendorSystemSettings){
        getRequestCache().setVendorSystemSettings(vendorSystemSettings);
    }

    public static HashMap<String, VendorSystemSetting> getVendorSystemSettings(){
        return getRequestCache().getVendorSystemSettings();
    }

    public static String getLang(){
        return StringUtils.isEmpty(getRequestCache()) || StringUtils.isEmpty(getRequestCache().getLang()) ? SpringMessage.msg("lang") : getRequestCache().getLang();
    }

    public static void setCommonRequestProperties(){
        String ipAddress = RequestUtil.getRequest().getParameter(CommonConstant.IP_ADDRESS);
        String userAgent = RequestUtil.getRequest().getParameter(CommonConstant.USER_AGENT);
        RequestUtil.setUserAgent(userAgent);
        RequestUtil.setIpAdress(ipAddress);
        //RequestUtil.setRequestBody(requestMap.get("data"));
        //RequestUtil.setRID(Security.encypt(getRequest().getRequestURI() + getRequest().getMethod() + getRequestBody()));
    }
}