package com.enenim.scaffold.service;

import com.enenim.scaffold.enums.LoginStatus;
import com.enenim.scaffold.exception.ScaffoldException;
import com.enenim.scaffold.exception.UnAuthorizedException;
import com.enenim.scaffold.model.cache.LoginCache;
import com.enenim.scaffold.model.dao.Login;
import com.enenim.scaffold.service.cache.LoginCacheService;
import com.enenim.scaffold.util.JsonConverter;
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
    private static final long EXPIRATION_TIME = 1000 * 60 * 20; //20mins//1000 * 60 * 60 * 24 * 7; // 7 days

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
        System.out.println("token in getToken = " + token);

        if(StringUtils.isEmpty(token))throw new UnAuthorizedException("invalid_token");
        return token.split(" ")[1];
    }

    public LoginCache decodeToken() {
        try {
            String token = getToken();
            System.out.println("decoded token = " + token);
            Claims claims;
            claims = Jwts.parser()
                    .setSigningKey(TOKEN_KEY)
                    .parseClaimsJws(token).getBody();
            System.out.println("claims = " + JsonConverter.getJsonRecursive(claims));
            Long id = Long.valueOf(String.valueOf(claims.get(ID)));
            System.out.println("tracker before conversion= " + JsonConverter.getJsonRecursive(claims.get(TRACKER)));

            Map<String, Object> tracker = new ObjectMapper().convertValue(claims.get(TRACKER), new TypeReference<Map<String, Object>>(){});

            System.out.println("tracker after conversion= " + tracker);
            String sessionId = (String) tracker.get("session_id");
            System.out.println("sessionId = " + sessionId);

            System.out.println(" loginCacheService.get(String.valueOf(id)) = " + JsonConverter.getJsonRecursive(loginCacheService.get(String.valueOf(id))));

            //System.out.println("loginCacheService.get(String.valueOf(id)).get(sessionId) = " + JsonConverter.getJsonRecursive(loginCacheService.get(String.valueOf(id)).get(sessionId)));

            if(StringUtils.isEmpty(loginCacheService.get(String.valueOf(id)))){
                throw new ScaffoldException("session_expired");
            }
            Object loginCache = loginCacheService.get(String.valueOf(id)).get(sessionId);

            System.out.println("loginCache = " + JsonConverter.getJsonRecursive(loginCache));

            return ObjectMapperUtil.map(loginCache, LoginCache.class);
        }catch (ExpiredJwtException e) {
            throw new UnAuthorizedException("expired_token");
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnAuthorizedException(e);
        }
    }

    public void refreshToken(LoginCache token){
        token.setCreated(new Date());
        saveToken(token);
    }

    @Async
    public void saveToken(LoginCache token){
        loginCacheService.save(token);
    }

    public void validateLoginStatus(Login login) {
        if(login.getStatus() == LoginStatus.DISABLED || login.getStatus() == LoginStatus.LOCKED){
            if(login.getStatus() == LoginStatus.DISABLED) throw new ScaffoldException("disabled_account");
            if(login.getStatus() == LoginStatus.LOCKED) throw new ScaffoldException("blocked_account");
        }
    }
}