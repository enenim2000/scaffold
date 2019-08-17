
package com.enenim.scaffold.interceptor;

import com.enenim.scaffold.annotation.DataDecrypt;
import com.enenim.scaffold.annotation.Permission;
import com.enenim.scaffold.annotation.Role;
import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.exception.ScaffoldException;
import com.enenim.scaffold.exception.UnAuthorizedException;
import com.enenim.scaffold.model.cache.LoginCache;
import com.enenim.scaffold.model.dao.Login;
import com.enenim.scaffold.model.dao.PaymentChannel;
import com.enenim.scaffold.model.dao.Staff;
import com.enenim.scaffold.service.ApiKeyService;
import com.enenim.scaffold.service.TokenAuthenticationService;
import com.enenim.scaffold.service.UserResolverService;
import com.enenim.scaffold.service.dao.ConsumerSettingService;
import com.enenim.scaffold.service.dao.LoginService;
import com.enenim.scaffold.service.dao.PaymentChannelService;
import com.enenim.scaffold.service.dao.TaskService;
import com.enenim.scaffold.shared.Channel;
import com.enenim.scaffold.util.JsonConverter;
import com.enenim.scaffold.util.RequestCache;
import com.enenim.scaffold.util.RequestUtil;
import com.enenim.scaffold.util.message.SpringMessage;
import com.enenim.scaffold.util.setting.SettingCacheService;
import com.enenim.scaffold.util.setting.SystemSetting;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final TokenAuthenticationService tokenAuthenticationService;
    private final SettingCacheService settingCacheService;
    private final UserResolverService userResolverService;
    private final ApiKeyService apiKeyService;
    private final PaymentChannelService paymentChannelService;
    private final TaskService taskService;
    private final LoginService loginService;
    private final ConsumerSettingService consumerSettingService;

    public AuthenticationInterceptor(TokenAuthenticationService tokenAuthenticationService, SettingCacheService settingCacheService, UserResolverService userResolverService, ApiKeyService apiKeyService, PaymentChannelService paymentChannelService, TaskService taskService, LoginService loginService, ConsumerSettingService consumerSettingService) {
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.settingCacheService = settingCacheService;
        this.userResolverService = userResolverService;
        this.apiKeyService = apiKeyService;
        this.paymentChannelService = paymentChannelService;
        this.taskService = taskService;
        this.loginService = loginService;
        this.consumerSettingService = consumerSettingService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /* Must be remove for production environment **/

        boolean isSwagger = request.getRequestURI().contains("/v2/api-docs")
                || request.getRequestURI().contains("/configuration/ui")
                || request.getRequestURI().contains("/swagger-resources")
                || request.getRequestURI().contains("/configuration/security")
                || request.getRequestURI().contains("/swagger-ui.html")
                || request.getRequestURI().contains("/webjars/");

        if(isSwagger && (request.getRemoteAddr().equals("0:0:0:0:0:0:0:1") || request.getRemoteAddr().equals("127.0.0.1"))){
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod)handler;

        PaymentChannel paymentChannel = validateApiKey();

        /*
         * Added to allow none secured route to be accessible
         */
        if(!isSecuredRoute(handlerMethod)) return true;

        LoginCache loginToken = validateToken();

        Login login = loginService.getLogin(loginToken.getId());

        tokenAuthenticationService.validateLoginStatus(login);

        Date date = new Date();

        RequestUtil.setSessionId(loginToken.getTracker().getSessionId());
        RequestUtil.setRequestCache(new RequestCache());

        tokenAuthenticationService.refreshToken(loginToken, date);
        tokenAuthenticationService.refreshTracker(loginToken.getTracker(), date);

        if(loginToken.getUserType().equalsIgnoreCase(RoleConstant.CONSUMER)){
            RequestUtil.setConsumerSystemSettings(consumerSettingService.getConsumerSystemSettingsMap( loginToken.getUserId(), RoleConstant.CONSUMER));
        }else if (loginToken.getUserType().equalsIgnoreCase(RoleConstant.VENDOR)){
            RequestUtil.setVendorSystemSettings(null);
        }

        InterceptorParamater interceptorParamater = new InterceptorParamater(request, response, handler);

        RequestUtil.setLoginToken(loginToken);
        RequestUtil.setLogin(login);
        RequestUtil.setLang(SpringMessage.msg("lang"));
        RequestUtil.setChannel(paymentChannel);

        if(handlerMethod.getMethod().isAnnotationPresent(Role.class)){
            validateRole(interceptorParamater);
        }else {
            userResolverService.setUserByRole(RequestUtil.getLogin().getUserType());
        }

        if(handlerMethod.getMethod().isAnnotationPresent(Permission.class) && RequestUtil.getLogin().getUserType().equalsIgnoreCase(RoleConstant.STAFF)){
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
        RequestUtil.removeRequestCache();
    }

    private PaymentChannel validateApiKey(){
        Channel channel = apiKeyService.validateKey(RequestUtil.getApiKey());
        Optional<PaymentChannel> paymentChannel = paymentChannelService.getPaymentChannelByCode(channel.getCode());
        if(!paymentChannel.isPresent()) throw new ScaffoldException("channel_not_configured");
        if(paymentChannel.get().getEnabled() != EnabledStatus.ENABLED) throw new ScaffoldException("channel_disabled");
        return paymentChannel.get();
    }

    /**
     * This method validate the token in the decodeToken() and store the token in the request object
     */
    private LoginCache validateToken(){

        LoginCache loginToken = tokenAuthenticationService.decodeToken();

        if(StringUtils.isEmpty(loginToken)) throw new UnAuthorizedException("invalid_token");

        SystemSetting systemSetting = settingCacheService.getSystemSetting("idle_timeout");

        long minIdleTimeout = Integer.valueOf(systemSetting.getDetail().getValue());

        if(loginToken.hasExpired(minIdleTimeout)){
            throw new ScaffoldException("session_expired");
        }

        return loginToken;
    }

    private void validateRole(InterceptorParamater interceptorParamater){
        HandlerMethod handlerMethod = (HandlerMethod)interceptorParamater.getHandler();
        String[] roles = handlerMethod.getMethod().getAnnotation(Role.class).value();
        String role = userResolverService.isValidRole(roles);
        userResolverService.setUserByRole(role);
    }

    private void validatePermission(InterceptorParamater interceptorParamater){
        String userType = RequestUtil.getLoginToken().getUserType();
        if(RoleConstant.STAFF.equalsIgnoreCase(userType)){
            Staff staff = RequestUtil.getStaff();
            HandlerMethod handlerMethod = (HandlerMethod)interceptorParamater.getHandler();
            String route = handlerMethod.getMethod().getAnnotation(Permission.class).value();
            log.info("Staff: {}", JsonConverter.getJsonRecursive(staff));
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
    private class InterceptorParamater {
        Object handler;
        ContentCachingRequestWrapper request;
        ContentCachingResponseWrapper response;

        private InterceptorParamater(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler){
            this.request = new ContentCachingRequestWrapper(httpServletRequest);
            this.response = new ContentCachingResponseWrapper(httpServletResponse);
            this.handler = handler;
            RequestUtil.setAuthorization(null);
            RequestUtil.setCommonRequestProperties();
        }
    }
}