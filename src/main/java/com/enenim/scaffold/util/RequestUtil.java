package com.enenim.scaffold.util;

import com.enenim.scaffold.constant.CommonConstant;
import com.enenim.scaffold.constant.RoleConstant;
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
     * @return IP Address from baseRequest; baseRequest.getIpAddress()
     */
    public static String getIpAddress(){
        return getRequest().getParameter(CommonConstant.IP_ADDRESS);
    }

    /**
     *
     * @return Browser from baseRequest; baseRequest.getUserAgent()
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
     * @return T data in BaseRequest<T>, request.setAttribute("request", concatenate(baseRequest.getData() + uri + datetime))//rid => is the concatenation of baseRequest, uri, datetime via AOP
     */
    public static String getRID(){
        return (String)getRequest().getAttribute("rid");
    }

    /**
     *
     * @return T data in BaseRequest<T>, request.setAttribute("request", baseRequest.getData())
     */
    public static Object getBaseRequestBody(){
        return getRequest().getAttribute("request");
    }

    public static String q(){
        return getRequest().getParameter(Q)==null?"":getRequest().getParameter(Q);
    }

    public static String to(){
        return getRequest().getParameter(TO)==null?"":getRequest().getParameter(TO);
    }

    public static String from(){
        return getRequest().getParameter(FROM)==null?"":getRequest().getParameter(FROM);
    }

    public static String perPage(){
        return getRequest().getParameter(PER_PAGE)==null?"":getRequest().getParameter(PER_PAGE);
    }

    public static int page(){
        return Integer.valueOf(getRequest().getParameter(PAGE) != null ? getRequest().getParameter(PAGE) : "1");
    }

    public static void setMessage(String message){
        getRequest().setAttribute(MESSAGE, message);
    }

    public static String getMessage(){
        return (String) getRequest().getAttribute(MESSAGE);
    }
}