package com.enenim.scaffold.service;

import com.enenim.scaffold.enums.LoginStatus;
import com.enenim.scaffold.exception.ScaffoldException;
import com.enenim.scaffold.exception.UnAuthorizedException;
import com.enenim.scaffold.model.cache.LoginCache;
import com.enenim.scaffold.model.dao.Login;
import com.enenim.scaffold.model.dao.Tracker;
import com.enenim.scaffold.service.cache.LoginCacheService;
import com.enenim.scaffold.service.dao.TrackerService;
import com.enenim.scaffold.util.JsonConverter;
import com.enenim.scaffold.util.RequestUtil;
import com.enenim.scaffold.util.message.SpringMessage;
import com.enenim.scaffold.util.setting.SettingCacheService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;

import static com.enenim.scaffold.constant.ModelFieldConstant.ID;

@Service
public class TokenAuthenticationService {
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 7 days

    private static final String HEADER_STRING = "Authorization";

    private final LoginCacheService loginCacheService;
    private final TrackerService trackerService;
    private final SettingCacheService settingCacheService;

    private static String TOKEN_KEY = SpringMessage.msg("jwt");

    @Autowired
    public TokenAuthenticationService(LoginCacheService loginCacheService, TrackerService trackerService, SettingCacheService settingCacheService) {
        this.loginCacheService = loginCacheService;
        this.trackerService = trackerService;
        this.settingCacheService = settingCacheService;
    }

    public String encodeToken(LoginCache loginToken){
        Map<String, Object> claims = new ObjectMapper().convertValue(loginToken, new TypeReference<Map<String, Object>>(){});
        return Jwts.builder()
            .setSubject(loginToken.getUsername())
            .setClaims(claims)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, TOKEN_KEY)
            .compact();
    }

    private String getToken(){
        String token = RequestUtil.getRequest().getHeader(HEADER_STRING);
        if(StringUtils.isEmpty(token))throw new UnAuthorizedException("invalid_token");
        return token.split(" ")[1];
    }

    public LoginCache decodeToken() {
        try {
            String token = getToken();
            Claims claims;
            claims = Jwts.parser()
                    .setSigningKey(TOKEN_KEY)
                    .parseClaimsJws(token).getBody();
            Long id = Long.valueOf(String.valueOf(claims.get(ID)));
            if(StringUtils.isEmpty(loginCacheService.get(String.valueOf(id)))){
                throw new ScaffoldException("invalid_token");
            }
            Map<String, Object> tracker = new ObjectMapper().convertValue(claims.get("tracker"), new TypeReference<Map<String, Object>>(){});
            String sessionId = (String) tracker.get("session_id");
            Object loginCache = loginCacheService.get(String.valueOf(id)).get(sessionId);
            if(StringUtils.isEmpty(loginCache)){
                throw new ScaffoldException("invalid_token");
            }

            System.out.println("loginCache = " + loginCache);

            return JsonConverter.getObject(loginCache, LoginCache.class);
        }catch (ExpiredJwtException e) {
            throw new UnAuthorizedException("expired_token");
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnAuthorizedException(e);
        }
    }

    public void refreshToken(LoginCache token, Date date){
        token.getTracker().setTimeOfLastActivity(date);
        saveToken(token);
    }

    @Async
    public void saveToken(LoginCache token){
        System.out.println("saveToken <<token>> = " + JsonConverter.getJsonRecursive(token));
        loginCacheService.save(token);
    }

    @Async
    public void refreshTracker(Tracker tracker, Date date){
        tracker.setTimeOfLastActivity(date);
        trackerService.saveTracker(tracker);
    }

    public void validateLoginStatus(Login login) {
        if(login.getStatus() == LoginStatus.DISABLED || login.getStatus() == LoginStatus.LOCKED){
            if(login.getStatus() == LoginStatus.DISABLED) throw new ScaffoldException("disabled_account");
            if(login.getStatus() == LoginStatus.LOCKED) throw new ScaffoldException("blocked_account");
        }
    }

    public boolean logout(){
        LoginCache loginToken = RequestUtil.getLoginToken();
        String loginId = String.valueOf(loginToken.getId());
        String sessionId = loginToken.getTracker().getSessionId();
        if(settingCacheService.multipleSessionIsEnabled()) {
            loginToken = loginCacheService.get(sessionId).get(sessionId);
            if(!StringUtils.isEmpty(loginToken)){
                loginCacheService.delete(loginToken.getId(), loginToken.getTracker().getSessionId());
                return true;
            }
        }else {
            loginToken = loginCacheService.get(sessionId).get(sessionId);
            if(!StringUtils.isEmpty(loginToken)){
                loginCacheService.delete(loginId);
                return true;
            }
            loginCacheService.delete(loginId);
        }
        return false;
    }
}