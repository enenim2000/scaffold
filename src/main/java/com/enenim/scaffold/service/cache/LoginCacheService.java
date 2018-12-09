package com.enenim.scaffold.service.cache;

import com.enenim.scaffold.model.cache.LoginCache;
import com.enenim.scaffold.repository.cache.LoginCacheRepository;
import com.enenim.scaffold.util.JsonConverter;
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
public class LoginCacheService implements LoginCacheRepository {

    private static final String LOGIN = "LoginCache";

    @Qualifier(value = "redisAuthenticationTemplate")
    private RedisTemplate<String, Map<String, LoginCache>> redisTemplate;

    private HashOperations<String, String, Map<String, LoginCache>> hashOps;

    public LoginCacheService(){}

    @Autowired
    private LoginCacheService(RedisTemplate<String, Map<String, LoginCache>> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOps = redisTemplate.opsForHash();
    }

    /**
     * This allows us to get all active session using a particular group
     * say all those currently using system, identified by h = login.getId()
     */
    @Override
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
        Map<String, LoginCache> loginsPerGroup = hashOps.get(LOGIN, h);
        if(!StringUtils.isEmpty(loginsPerGroup) && !loginsPerGroup.entrySet().isEmpty()){
            System.out.println(" loginToken in get(hk) = " + JsonConverter.getJsonRecursive(loginsPerGroup.get(hk)));
            return loginsPerGroup.get(hk);
        }
        return null;
    }

    @Override
    public List<Map<String, LoginCache>> get() {
        return hashOps.values(LOGIN);
    }

    @Override
    public Map<String, Map<String, LoginCache>> getAll() {
        return hashOps.entries(LOGIN);
    }

    @Override
    public void save(Map<String, LoginCache> data) {
        if(multipleLoginIsEnabled())throw new UnsupportedOperationException("Not allowed when multiple login is enabled");
        save(data.entrySet().iterator().next().getValue());
    }

    /**
     *
     * @param id points getTo the key that holds login sessions for a app group
     * when multiple login is enabled
     */
    @Override
    public void delete(String id) {
        if(multipleLoginIsEnabled())throw new UnsupportedOperationException("Not allowed when multiple login is enabled");
        delete(id, null);
    }

    public void save(LoginCache data) {
        System.out.println(" loginToken in save = " + JsonConverter.getJsonRecursive(data));
        String id = String.valueOf(data.getId());
        Map<String, LoginCache> records = new HashMap<>();

        if(multipleLoginIsEnabled()){
            records = this.get(id);
            if(StringUtils.isEmpty(records)){
                records = new HashMap<>();
            }
        }

        records.put(data.getSessionId(), data);
        hashOps.put(LOGIN, id, records);
    }

    public void delete(String id, String sessionId) {
        if(StringUtils.isEmpty(sessionId)){
            hashOps.delete(LOGIN, id);
        }else {
            Map<String, LoginCache> records = this.get(id);
            records.remove(sessionId);
            hashOps.put(LOGIN, id, records);
        }
    }

    private boolean multipleLoginIsEnabled(){
        return true;
    }
}
