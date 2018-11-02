package com.enenim.scaffold.service;

import com.enenim.scaffold.enums.LoginStatus;
import com.enenim.scaffold.exception.ScaffoldException;
import com.enenim.scaffold.exception.UnAuthorizedException;
import com.enenim.scaffold.model.cache.LoginCache;
import com.enenim.scaffold.model.dao.Login;
import com.enenim.scaffold.model.dao.Tracker;
import com.enenim.scaffold.service.cache.LoginCacheService;
import com.enenim.scaffold.util.ObjectMapperUtil;
import com.enenim.scaffold.util.RequestUtil;
import com.enenim.scaffold.util.message.SpringMessage;
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

    private static final String TRACKER = "tracker";

    private static final String HEADER_STRING = "Authorization";

    private final LoginCacheService loginCacheService;

    private static String TOKEN_KEY = SpringMessage.msg("jwt");

    @Autowired
    public TokenAuthenticationService(LoginCacheService loginCacheService) {
        this.loginCacheService = loginCacheService;
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
        if(StringUtils.isEmpty(token))throw new UnAuthorizedException("invalid_token_request");
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
            //return new ObjectMapper().convertValue(claims.get("login"), LoginToken.class);
            Tracker tracker = ObjectMapperUtil.map(claims.get(TRACKER), Tracker.class);
            String sessionId = tracker.getSessionId();
            return loginCacheService.get(String.valueOf(id)).get(sessionId);
        }catch (ExpiredJwtException e) {
            throw new UnAuthorizedException("expired_token");
        } catch (Exception e) {
            throw new UnAuthorizedException(e);
        }
    }

    @Async
    public void refreshToken(LoginCache token){
        token.setCreated(new Date());
        saveToken(token);
    }

    public void saveToken(LoginCache token){
        loginCacheService.save(token);
    }

    public void validateLoginStatus(Login login) {
        if(login.getStatus() == LoginStatus.DISABLED || login.getStatus() == LoginStatus.LOCKED){
            if(login.getStatus() == LoginStatus.DISABLED)throw new ScaffoldException("disabled_account");
            if(login.getStatus() == LoginStatus.LOCKED)throw new ScaffoldException("blocked_account");
        }
    }
}