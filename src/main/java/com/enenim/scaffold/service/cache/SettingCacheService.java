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

    public SettingCache get(String key, String id){
        return hashOps.get(key, id);
    }

    public List<SettingCache> get(String key) {
        return hashOps.values(key);
    }

    public Map<String, SettingCache> getAll(String key) {
        return hashOps.entries(key);
    }

    public void save(String key, SettingCache data){
        hashOps.put(key, data.getKey(), data);
    }

    public void save(String key, Map<String, SettingCache> data){
        hashOps.putAll(key, data);
    }

    public void delete(String key, String id) {
        hashOps.delete(key, id);
    }

    public void deleteAll(String key) {
        hashOps.delete(key);
    }
}
