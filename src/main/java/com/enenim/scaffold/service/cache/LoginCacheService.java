package com.enenim.scaffold.service.cache;

import com.enenim.scaffold.model.cache.LoginCache;
import com.enenim.scaffold.repository.cache.LoginCacheRepository;
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

    private static final String KEY = "LoginCache";

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

    @Override
    public Map<String, LoginCache> get(String id) {
        return hashOps.get(KEY, id);
    }

    @Override
    public List<Map<String, LoginCache>> get() {
        return hashOps.values(KEY);
    }

    @Override
    public Map<String, Map<String, LoginCache>> getAll() {
        return hashOps.entries(KEY);
    }

    @Override
    public void save(Map<String, LoginCache> data) {
        if(multipleLoginIsEnabled())throw new UnsupportedOperationException("Not allowed when multiple login is enabled");
        save(data.entrySet().iterator().next().getValue());
    }

    /**
     *
     * @param id points to the key that holds login sessions for a user group
     * when multiple login is enabled
     */
    @Override
    public void delete(String id) {
        if(multipleLoginIsEnabled())throw new UnsupportedOperationException("Not allowed when multiple login is enabled");
        delete(id, null);
    }

    public void save(LoginCache data) {
        String id = String.valueOf(data.getId());
        Map<String, LoginCache> records = this.get(id);
        if(StringUtils.isEmpty(records) || !multipleLoginIsEnabled()){
            records = new HashMap<>();
        }
        records.put(data.getTracker().getSessionId(), data);
        hashOps.put(KEY, id, records);
    }

    public void delete(String id, String sessionId) {
        if(StringUtils.isEmpty(sessionId)){
            hashOps.delete(KEY, id);
        }else {
            Map<String, LoginCache> records = this.get(id);
            records.remove(sessionId);
            hashOps.put(KEY, id, records);
        }
    }

    private boolean multipleLoginIsEnabled(){
        return true;
    }
}
