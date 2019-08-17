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
        //String token = RequestUtil.getRequest().getHeader(HEADER_STRING);
        String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJTeXN0ZW0iLCJ0cmFja2VyIjp7ImlkIjoxOCwibG9naW4iOnsiaWQiOjEsInVzZXJuYW1lIjoiU3lzdGVtIiwic3RhdHVzIjoiRU5BQkxFRCIsInZlcmlmeVN0YXR1cyI6IlZFUklGSUVEIiwic2VjcmV0UXVlc3Rpb24iOm51bGwsImNyZWF0ZWRfYXQiOiJXZWQsIDE0IEF1ZyAyMDE5IDAwOjAwOjAwIFdBVCIsInVwZGF0ZWRfYXQiOiJXZWQsIDE0IEF1ZyAyMDE5IDAwOjAwOjAwIFdBVCIsInVzZXJfdHlwZSI6IlN0YWZmIiwidXNlcl9pZCI6MiwibGFzdF9sb2dnZWRfaW4iOm51bGx9LCJjcmVhdGVkX2F0IjoiU2F0LCAxNyBBdWcgMjAxOSAxMzoxODowNiBXQVQiLCJ1cGRhdGVkX2F0IjoiU2F0LCAxNyBBdWcgMjAxOSAxMzoxODowNiBXQVQiLCJzZXNzaW9uX2lkIjoiMmExMG0xMEkyTWZzUVZrZUhaTWZrbHlDYXU1ZjlRVU1hdDRiRWJDMXFoWHROdzZ4aHgxeXczQjYiLCJpcF9hZGRyZXNzIjpudWxsLCJ1c2VyX2FnZW50IjpudWxsLCJkYXRlX2xvZ2dlZF9pbiI6IlNhdCwgMTcgQXVnIDIwMTkgMTM6MTg6MDYgV0FUIiwiZGF0ZV9sb2dnZWRfb3V0IjpudWxsLCJ0aW1lX29mX2xhc3RfYWN0aXZpdHkiOiJTYXQsIDE3IEF1ZyAyMDE5IDEzOjE4OjA2IFdBVCIsImZhaWxlZF9hdHRlbXB0cyI6MCwibG9nZ2VkX2luIjoiVVNFUl9MT0dHRURfSU4ifSwidXNlcl90eXBlIjoiU3RhZmYiLCJ1c2VyIjp7ImlkIjoyLCJlbWFpbCI6ImVuZW5pbTIwMDBAZ21haWwuY29tIiwiZW5hYmxlZCI6IkVOQUJMRUQiLCJicmFuY2giOnsiaWQiOjEsInNvbCI6Ijc3MDkzMDc4NDQiLCJuYW1lIjoiSGVhZCBPZmZpY2UiLCJhZGRyZXNzIjoiMjUvMjcgQWRleWVtbyBBbGFraWphIiwiZW5hYmxlZCI6IkVOQUJMRUQiLCJjcmVhdGVkX2F0IjoiV2VkLCAxNCBBdWcgMjAxOSAwMDowMDowMCBXQVQiLCJ1cGRhdGVkX2F0IjoiV2VkLCAxNCBBdWcgMjAxOSAwMDowMDowMCBXQVQifSwiZ3JvdXAiOnsiaWQiOjEsIm5hbWUiOiJTeXN0ZW0iLCJyb2xlIjoiU3lzdGVtIiwiZW5hYmxlZCI6IkVOQUJMRUQiLCJjcmVhdGVkX2F0IjoiV2VkLCAxNCBBdWcgMjAxOSAwMDowMDowMCBXQVQiLCJ1cGRhdGVkX2F0IjoiV2VkLCAxNCBBdWcgMjAxOSAwMDowMDowMCBXQVQiLCJhY3RpdmVfaG91ciI6eyJpZCI6MSwibmFtZSI6IkRhaWx5IiwiZW5hYmxlZCI6IkVOQUJMRUQiLCJjcmVhdGVkX2F0IjoiV2VkLCAxNCBBdWcgMjAxOSAwMDowMDowMCBXQVQiLCJ1cGRhdGVkX2F0IjoiV2VkLCAxNCBBdWcgMjAxOSAwMDowMDowMCBXQVQiLCJiZWdpbl90aW1lIjoiMDg6MDA6MDAiLCJlbmRfdGltZSI6IjIzOjU5OjAwIn0sImhvbGlkYXlfbG9naW4iOiJZRVMiLCJ3ZWVrZW5kX2xvZ2luIjoiWUVTIn0sImNyZWF0ZWRfYXQiOiJXZWQsIDE0IEF1ZyAyMDE5IDAwOjAwOjAwIFdBVCIsInVwZGF0ZWRfYXQiOiJXZWQsIDE0IEF1ZyAyMDE5IDAwOjAwOjAwIFdBVCIsImVtcGxveWVlX2lkIjoiMTAwMSIsImFjdGl2ZV9ob3VyIjp7ImlkIjoxLCJuYW1lIjoiRGFpbHkiLCJlbmFibGVkIjoiRU5BQkxFRCIsImNyZWF0ZWRfYXQiOiJXZWQsIDE0IEF1ZyAyMDE5IDAwOjAwOjAwIFdBVCIsInVwZGF0ZWRfYXQiOiJXZWQsIDE0IEF1ZyAyMDE5IDAwOjAwOjAwIFdBVCIsImJlZ2luX3RpbWUiOiIwODowMDowMCIsImVuZF90aW1lIjoiMjM6NTk6MDAifSwiZnVsbG5hbWUiOiJFbmVuaW0gQXN1a3dvIiwiaG9saWRheV9sb2dpbiI6IllFUyIsIndlZWtlbmRfbG9naW4iOiJZRVMifSwidXNlcl9pZCI6MiwibG9naW5fc3RhdHVzIjoiRU5BQkxFRCIsImxvZ2dlZF9pbiI6IlVTRVJfTE9HR0VEX0lOIiwibGFzdF9sb2dnZWRfaW4iOjE1NjYwNDQyODY2MDAsInN0YXR1cyI6IkVOQUJMRUQiLCJnbG9iYWxfc2V0dGluZ3MiOnt9LCJjcmVhdGVkX2F0IjpudWxsLCJ1cGRhdGVkX2F0IjpudWxsLCJleHAiOjE1NjY2NDkwODZ9._HmY3g_xFCuTiqN3B8w1_fGbPwD15RI2JqVX_weAiRnmbFs7Z7TgCx_a_PmC-uukvuQ5x1nAc_qtJAMPAph-mQ";
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

    public void saveToken(LoginCache token){
        loginCacheService.save(token);
    }

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