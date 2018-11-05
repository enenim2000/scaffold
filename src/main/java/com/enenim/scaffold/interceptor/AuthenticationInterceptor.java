
package com.enenim.scaffold.interceptor;

import ch.qos.logback.classic.gaffer.PropertyUtil;
import com.enenim.scaffold.annotation.DataDecrypt;
import com.enenim.scaffold.annotation.Permission;
import com.enenim.scaffold.annotation.Role;
import com.enenim.scaffold.exception.ScaffoldException;
import com.enenim.scaffold.exception.UnAuthorizedException;
import com.enenim.scaffold.model.cache.LoginCache;
import com.enenim.scaffold.model.cache.SettingCache;
import com.enenim.scaffold.model.dao.Login;
import com.enenim.scaffold.model.dao.Staff;
import com.enenim.scaffold.service.TokenAuthenticationService;
import com.enenim.scaffold.service.UserResolverService;
import com.enenim.scaffold.service.dao.LoginService;
import com.enenim.scaffold.util.RequestUtil;
import com.enenim.scaffold.util.setting.SettingCacheCoreService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final TokenAuthenticationService tokenAuthenticationService;
    private final LoginService loginService;
    private final SettingCacheCoreService settingCacheCoreService;
    private final UserResolverService userResolverService;

    public AuthenticationInterceptor(TokenAuthenticationService tokenAuthenticationService, LoginService loginService, SettingCacheCoreService settingCacheCoreService, UserResolverService userResolverService) {
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.loginService = loginService;
        this.settingCacheCoreService = settingCacheCoreService;
        this.userResolverService = userResolverService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        validateApiKey();

        validateToken();

        InterceptorParamater interceptorParamater = new InterceptorParamater().setHttpServletRequest(request).setHttpServletResponse(response).setHandler(handler);

        HandlerMethod handlerMethod = (HandlerMethod)handler;

        if(handlerMethod.getMethod().isAnnotationPresent(Role.class)){
            validateRole(interceptorParamater);
        }

        if(handlerMethod.getMethod().isAnnotationPresent(Permission.class)){
            validatePermission(interceptorParamater);
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

    }

    /**
     * This method validate the token in the decodeToken() and store the token in the request object
     */
    private void validateToken(){

        LoginCache loginToken = tokenAuthenticationService.decodeToken();
        if(StringUtils.isEmpty(loginToken))throw new UnAuthorizedException("unauthorized");

        SettingCache settingCache = settingCacheCoreService.getCoreSetting("idle_timeout");
        if(loginToken.hasExpired(Long.valueOf(settingCache.getValue())))throw new ScaffoldException("session_expired");

        Login login = loginService.getLogin(loginToken.getId());

        if(!StringUtils.isEmpty(login))throw new UnAuthorizedException("unauthorized");

        tokenAuthenticationService.validateLoginStatus(login);

        tokenAuthenticationService.refreshToken(loginToken);

        RequestUtil.setLoginToken(loginToken);
        RequestUtil.setLogin(login);
    }

    private void validateRole(InterceptorParamater interceptorParamater){
        HandlerMethod handlerMethod = (HandlerMethod)interceptorParamater.getHandler();
        String roles = handlerMethod.getMethod().getAnnotation(Role.class).value();
        if(!userResolverService.isValidRole(roles))throw new ScaffoldException("access_denied");
    }

    private void validatePermission(InterceptorParamater interceptorParamater){
        String userType = userResolverService.formatUserType(RequestUtil.getLoginToken().getUserType());
        if(userType.equalsIgnoreCase(ActorConstant.STAFF)){
            Staff staff = (Staff) RequestUtil.loginToken.getUser();
            HandlerMethod handlerMethod = (HandlerMethod)interceptorParamater.getHandler();
            String route = handlerMethod.getMethod().getAnnotation(Permission.class).value();
            if(!staff.getGroup().getTasks().contains( taskService.getTaskByRoute(route) )) {
                throw new UnAuthorizedException(PropertyUtil.msg("unauthorized"));
            }
        }
    }

    private void decrypt(InterceptorParamater interceptorParamater){

    }

    private class InterceptorParamater{
        HttpServletRequest httpServletRequest;
        HttpServletResponse httpServletResponse;
        Object handler;

        public HttpServletRequest getHttpServletRequest() {
            return httpServletRequest;
        }

        private InterceptorParamater setHttpServletRequest(HttpServletRequest httpServletRequest) {
            this.httpServletRequest = httpServletRequest;
            return this;
        }

        private HttpServletResponse getHttpServletResponse() {
            return httpServletResponse;
        }

        private InterceptorParamater setHttpServletResponse(HttpServletResponse httpServletResponse) {
            this.httpServletResponse = httpServletResponse;
            return this;
        }

        private Object getHandler() {
            return handler;
        }

        private InterceptorParamater setHandler(Object handler) {
            this.handler = handler;
            return this;
        }
    }
}