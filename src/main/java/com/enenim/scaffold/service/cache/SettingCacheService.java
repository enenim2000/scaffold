package com.enenim.scaffold.service.cache;

import com.enenim.scaffold.model.cache.SettingCache;
import com.enenim.scaffold.repository.cache.SettingCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Service
public class SettingCacheService implements SettingCacheRepository{

    private static final String KEY = "SettingCache";

    @Qualifier(value = "redisSettingTemplate")
    private RedisTemplate<String, SettingCache> redisTemplate;

    private HashOperations<String, String, SettingCache> hashOps;

    public SettingCacheService(){}

    @Autowired
    private SettingCacheService(RedisTemplate<String, SettingCache> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    @PostConstruct
    private void init() {
        hashOps = redisTemplate.opsForHash();
    }

    @Override
    public SettingCache get(String id) {
        return hashOps.get(KEY, id);
    }

    @Override
    public List<SettingCache> get() {
        return hashOps.values(KEY);
    }

    @Override
    public Map<String, SettingCache> getAll() {
        return hashOps.entries(KEY);
    }

    @Override
    public void save(SettingCache data) {
        hashOps.put(KEY, String.valueOf(data.getId()), data);
    }

    @Override
    public void delete(String id) {
        hashOps.delete(KEY, id);
    }
}
