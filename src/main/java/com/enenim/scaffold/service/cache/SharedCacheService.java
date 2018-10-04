package com.enenim.scaffold.service.cache;

import com.enenim.scaffold.repository.cache.SharedCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Service(value = "sharedCacheService")
public class SharedCacheService implements SharedCacheRepository{

    @Qualifier(value = "redisSharedTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    private HashOperations<String, String, Object> hashOps;

    public SharedCacheService(){}

    @Autowired
    private SharedCacheService(@Qualifier("redisSharedTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    @PostConstruct
    private void init() {
        hashOps = redisTemplate.opsForHash();
    }

    @Override
    public Object get(String h, String hk) {
        return hashOps.get(h, hk);
    }

    @Override
    public List<Object> get(String h) {
        return hashOps.values(h);
    }

    @Override
    public Map<String, Object> getAll(String h) {
        return hashOps.entries(h);
    }

    @Override
    public void save(String h, String hk, Object hv) {
        hashOps.put(h, hk, hv);
    }

    @Override
    public void delete(String h, String hk) {
        hashOps.delete(h, hk);
    }

    @Override
    public void deleteAll(String h) {
        hashOps.delete(h);
    }
}
