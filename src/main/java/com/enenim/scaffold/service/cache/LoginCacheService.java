package com.enenim.scaffold.service.cache;

import com.enenim.scaffold.model.cache.LoginCache;
import com.enenim.scaffold.util.setting.SettingCacheCoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginCacheService {

    private static final String LOGIN = "LoginCache";

    @Qualifier(value = "redisAuthenticationTemplate")
    private RedisTemplate<String, Map<String, LoginCache>> redisTemplate;

    private HashOperations<String, String, Map<String, LoginCache>> hashOps;

    private final SettingCacheCoreService settingCacheCoreService;

    @Autowired
    private LoginCacheService(RedisTemplate<String, Map<String, LoginCache>> redisTemplate, SettingCacheCoreService settingCacheCoreService) {
        this.redisTemplate = redisTemplate;
        this.settingCacheCoreService = settingCacheCoreService;
    }

    @PostConstruct
    private void init() {
        hashOps = redisTemplate.opsForHash();
    }

    /**
     * This allows us to get all active session using a particular group
     * say all those currently using system, identified by h = login.getId()
     */
    public Map<String, LoginCache> get(String h) {
        return hashOps.get(LOGIN, h);
    }

    /**
     *
     * @param h = login.id
     * @param hk = login.tracker.sessionId
     * @return LoginCache
     */
    public LoginCache get(String h, String hk) {
        System.out.println("h = " + h);
        System.out.println("hk = " + hk);
        Map<String, LoginCache> loginsPerGroup = hashOps.get(LOGIN, h);
        if(!StringUtils.isEmpty(loginsPerGroup) && !loginsPerGroup.entrySet().isEmpty()){
            return loginsPerGroup.get(hk);
        }
        return null;
    }

    public List<Map<String, LoginCache>> get() {
        return hashOps.values(LOGIN);
    }

    public Map<String, Map<String, LoginCache>> getAll() {
        return hashOps.entries(LOGIN);
    }

    public void save(Map<String, LoginCache> data) {
        if(settingCacheCoreService.multipleSessionIsEnabled())throw new UnsupportedOperationException("Not allowed when multiple login session is enabled");
        save(data.entrySet().iterator().next().getValue());
    }

    /**
     *
     * @param id points getTo the key that holds login sessions for a app group
     * when multiple login is enabled
     */
    public void delete(String id) {
        if(settingCacheCoreService.multipleSessionIsEnabled()) throw new UnsupportedOperationException("Not allowed when multiple login is enabled");
        delete(Long.valueOf(id), null);
    }

    public void save(LoginCache data) {
        String id = String.valueOf(data.getId());
        Map<String, LoginCache> records = new HashMap<>();

        if(settingCacheCoreService.multipleSessionIsEnabled()){
            records = this.get(id);
            if(StringUtils.isEmpty(records)){
                records = new HashMap<>();
            }
        }

        records.put(data.getTracker().getSessionId(), data);
        hashOps.put(LOGIN, id, records);
    }

    public void delete(Long loginId, String sessionId) {
        String id = String.valueOf(loginId);
        if(StringUtils.isEmpty(sessionId)){
            hashOps.delete(LOGIN, id);
        }else {
            Map<String, LoginCache> records = this.get(id);
            records.remove(sessionId);
            hashOps.put(LOGIN, id, records);
        }
    }
}
