
package com.enenim.scaffold.interceptor;

import com.enenim.scaffold.annotation.DataDecrypt;
import com.enenim.scaffold.annotation.Permission;
import com.enenim.scaffold.annotation.Role;
import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.exception.ScaffoldException;
import com.enenim.scaffold.exception.UnAuthorizedException;
import com.enenim.scaffold.model.cache.LoginCache;
import com.enenim.scaffold.model.cache.SettingCache;
import com.enenim.scaffold.model.dao.PaymentChannel;
import com.enenim.scaffold.model.dao.Staff;
import com.enenim.scaffold.service.ApiKeyService;
import com.enenim.scaffold.service.TokenAuthenticationService;
import com.enenim.scaffold.service.UserResolverService;
import com.enenim.scaffold.service.dao.PaymentChannelService;
import com.enenim.scaffold.service.dao.TaskService;
import com.enenim.scaffold.shared.Channel;
import com.enenim.scaffold.util.RequestUtil;
import com.enenim.scaffold.util.message.SpringMessage;
import com.enenim.scaffold.util.setting.SettingCacheCoreService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final TokenAuthenticationService tokenAuthenticationService;
    private final SettingCacheCoreService settingCacheCoreService;
    private final UserResolverService userResolverService;
    private final ApiKeyService apiKeyService;
    private final PaymentChannelService paymentChannelService;
    private final TaskService taskService;

    public AuthenticationInterceptor(TokenAuthenticationService tokenAuthenticationService, SettingCacheCoreService settingCacheCoreService, UserResolverService userResolverService, ApiKeyService apiKeyService, PaymentChannelService paymentChannelService, TaskService taskService) {
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.settingCacheCoreService = settingCacheCoreService;
        this.userResolverService = userResolverService;
        this.apiKeyService = apiKeyService;
        this.paymentChannelService = paymentChannelService;
        this.taskService = taskService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /*
         * Added for swagger to work smoothly
         */
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        validateApiKey();

        HandlerMethod handlerMethod = (HandlerMethod)handler;

        InterceptorParamater interceptorParamater = new InterceptorParamater(request, response, handler);

        /*
         * Added to allow none secured route to be accessible
         */
        if(!isSecuredRoute(handlerMethod)) return true;

        validateToken();

        if(handlerMethod.getMethod().isAnnotationPresent(Role.class)){
            validateRole(interceptorParamater);
        }

        if(handlerMethod.getMethod().isAnnotationPresent(Permission.class)){
            validatePermission(interceptorParamater);
            RequestUtil.setTaskRoute(handlerMethod.getMethod().getAnnotation(Permission.class).value());
        }

        if(handlerMethod.getMethod().isAnnotationPresent(DataDecrypt.class)){
            decrypt(interceptorParamater);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private void validateApiKey(){
        Channel channel = apiKeyService.validateKey(RequestUtil.getApiKey());
        Optional<PaymentChannel> paymentChannel = paymentChannelService.getPaymentChannelByCode(channel.getCode());
        if(!paymentChannel.isPresent()) throw new ScaffoldException("channel_not_configured");
        if(paymentChannel.get().getEnabled() != EnabledStatus.ENABLED) throw new ScaffoldException("channel_disabled");
        RequestUtil.setChannel(paymentChannel.get());
    }

    /**
     * This method validate the token in the decodeToken() and store the token in the request object
     */
    private void validateToken(){

        LoginCache loginToken = tokenAuthenticationService.decodeToken();

        if(StringUtils.isEmpty(loginToken)) throw new UnAuthorizedException("invalid_token_request");

        SettingCache settingCache = settingCacheCoreService.getCoreSetting("idle_timeout");

        if(loginToken.hasExpired(Long.valueOf(settingCache.getValue())))throw new ScaffoldException("session_expired");

        tokenAuthenticationService.validateLoginStatus(loginToken.getTracker().getLogin());

        tokenAuthenticationService.refreshToken(loginToken);

        RequestUtil.setLoginToken(loginToken);

        RequestUtil.setLogin(loginToken.getTracker().getLogin());
    }

    private void validateRole(InterceptorParamater interceptorParamater){
        HandlerMethod handlerMethod = (HandlerMethod)interceptorParamater.getHandler();
        String[] roles = handlerMethod.getMethod().getAnnotation(Role.class).value();
        String role = userResolverService.isValidRole(roles);
        if(StringUtils.isEmpty(role))throw new UnAuthorizedException("invalid_role", role);
        userResolverService.setUserByRole(role);
    }

    private void validatePermission(InterceptorParamater interceptorParamater){
        String userType = RequestUtil.getLoginToken().getUserType();
        if(RoleConstant.STAFF.equalsIgnoreCase(userType)){
            Staff staff = RequestUtil.getStaff();
            HandlerMethod handlerMethod = (HandlerMethod)interceptorParamater.getHandler();
            String route = handlerMethod.getMethod().getAnnotation(Permission.class).value();

            if(!staff.getGroup().getRole().equalsIgnoreCase("System")){
                if(!staff.getGroup().getTasks().contains( taskService.getTaskByRoute(route) )) {
                    throw new ScaffoldException("access_denied", HttpStatus.FORBIDDEN);
                }
            }
        }
    }

    private void decrypt(InterceptorParamater interceptorParamater){

    }

    private boolean isSecuredRoute(HandlerMethod handlerMethod){
        return handlerMethod.getMethod().isAnnotationPresent(Role.class) || handlerMethod.getMethod().isAnnotationPresent(Permission.class);
    }

    @Data
    private class InterceptorParamater{
        Object handler;
        ContentCachingRequestWrapper request;
        ContentCachingResponseWrapper response;

        private InterceptorParamater(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler){
            this.request = new ContentCachingRequestWrapper(httpServletRequest);
            this.response = new ContentCachingResponseWrapper(httpServletResponse);
            this.handler = handler;
            RequestUtil.setLang(SpringMessage.msg("lang"));
            RequestUtil.setAuthorization(null);
        }
    }
}