package com.enenim.scaffold.service.cache;

import com.enenim.scaffold.util.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service(value = "sharedExpireCacheService")
public class SharedExpireCacheService{

    public static final String SEPARATOR = "::";

    public static final String SINGUP = "signup";

    private static final Map<String, Long> durations = new HashMap<String, Long>() {{
        put(SINGUP, 2L);
    }};

    private static final Map<String, TimeUnit> units = new HashMap<String, TimeUnit>() {{
        put(SINGUP, TimeUnit.HOURS);
    }};

    @Qualifier(value = "redisSharedExpireTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    private ValueOperations<String, Object> valueOps;

    public SharedExpireCacheService(){}

    @Autowired
    private SharedExpireCacheService(@Qualifier("redisSharedExpireTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    @PostConstruct
    private void init() {
        valueOps = redisTemplate.opsForValue();
    }

    public Object get(String key) {
        return valueOps.get(key);
    }

    @Async
    public void put(String key, Object value) {

        System.out.println("key = " + key);
        System.out.println("value = " + JsonConverter.getJsonRecursive(value));
        String cachePrefix = getPrefix(key);
        System.out.println("cachePrefix = " + cachePrefix);
        if(StringUtils.isEmpty(valueOps)){
            System.out.println(" valueOps is empty ");
        }
        valueOps.set(key, value, durations.get(cachePrefix), units.get(cachePrefix));
    }

    @Async
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    private String getPrefix(String key){
        return key.split(SEPARATOR)[0];
    }
}
