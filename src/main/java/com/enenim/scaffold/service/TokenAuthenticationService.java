package com.enenim.scaffold.service;

import ch.qos.logback.classic.gaffer.PropertyUtil;
import com.enenim.scaffold.enums.LoginStatus;
import com.enenim.scaffold.model.dao.Login;
import com.enenim.scaffold.util.CommonUtil;
import com.enenim.scaffold.util.JsonConverter;
import com.enenim.scaffold.util.RequestUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;

@Service
public class TokenAuthenticationService {
    /**
     * To be flushed periodically via CronJob based on the session idle timeout
     */
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 1 day

    private static final String TRACKER = "tracker";

    private static final String TOKEN_PREFIX = "Scaffold";

    private static final String HEADER_STRING = "Authorization";

    private final RedisAuthenticationService redisAuthenticationService;

    private static String TOKEN_KEY = PropertyUtil.msg("jwt");

    @Autowired
    public TokenAuthenticationService(RedisAuthenticationService redisAuthenticationService) {
        this.redisAuthenticationService = redisAuthenticationService;
    }

    private Map<String, Object> getClaims(LoginToken login){
        return CommonUtil.toMap(login);
    }

    public String encodeToken(LoginToken loginToken){
        System.out.println("TOKEN_KEY = " + TOKEN_KEY);

        String token = Jwts.builder()
                .setSubject(loginToken.getUsername())
                .setClaims(getClaims(loginToken))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, TOKEN_KEY)
                .compact();

        System.out.println("\n\n\n ... token before saving = " + token);

        saveToken(loginToken);

        System.out.println("\n\n\n ... token after saving = " + token);

        return token;
    }

    private String getToken(){
        return validateToken();
    }

    public LoginToken decodeToken() {
        String token = getToken();
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(TOKEN_KEY)
                    .parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e) {
            return null;
        } catch ( Exception e) {
            e.printStackTrace();
            return null;
        }
        System.out.println("claims.get(ID) = " + claims.get(ID));
        Long id = Long.valueOf(String.valueOf(claims.get(ID)));
        System.out.println("id = " + id);

        Tracker tracker = ObjectMapperUtils.map(claims.get(TRACKER), Tracker.class);

        System.out.println("tracker = " + JsonConverter.getJsonRecursive(tracker));

        String sessionId = tracker.getSessionId();
        System.out.println("sessionId = " + sessionId);
        return redisAuthenticationService.getLoginToken(id, sessionId);
    }

    private String validateToken(){
        String messageKey = "invalid_token_request";
        String token = RequestUtil.getRequest().getHeader(HEADER_STRING);
        if(StringUtils.isEmpty(token))throw new UnAuthorizedException(PropertyUtil.msg(messageKey));

        String[] tokenParts = token.split(" ");
        if(!TOKEN_PREFIX.equalsIgnoreCase(tokenParts[0]))throw new UnAuthorizedException(PropertyUtil.msg(messageKey));
        if(StringUtils.isEmpty(tokenParts[1]))throw new UnAuthorizedException(PropertyUtil.msg(messageKey));
        token = tokenParts[1].trim();

        return token;
    }

    private void saveToken(LoginToken token){
        redisAuthenticationService.saveLoginToken(token);
    }

    public boolean isExpired(Long loginId, String sessionId){
        return redisAuthenticationService.isExpired(loginId, sessionId);
    }

    public void refresh(LoginToken token) {
        redisAuthenticationService.saveLoginToken(token);
    }

    public void validateLoginStatus(Login login) {
        if(login.getStatus() == LoginStatus.DISABLED || login.getStatus() == LoginStatus.LOCKED){
            if(login.getStatus() == LoginStatus.DISABLED)throw new BAPException("disabled_account");
            if(login.getStatus() == LoginStatus.LOCKED)throw new BAPException("blocked_account");
        }
    }
}