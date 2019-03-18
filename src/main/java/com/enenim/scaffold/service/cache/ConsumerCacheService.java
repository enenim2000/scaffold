package com.enenim.scaffold.service.cache;

import com.enenim.scaffold.util.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ConsumerCacheService {

    @Qualifier(value = "redisConsumerSettingTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    private HashOperations<String, String, Object> hashOps;

    private final static String KEY = "Consumer";

    public ConsumerCacheService(){}

    @Autowired
    private ConsumerCacheService(@Qualifier("redisConsumerSettingTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOps = redisTemplate.opsForHash();
    }

    public Object getConsumerSetting(Long consumerId) {
        return hashOps.get(KEY, consumerId);
    }

    public void saveConsumerSetting(Long consumerId, Object consumerSettings) {
        hashOps.put(KEY, String.valueOf(consumerId), JsonConverter.getJson(consumerSettings));
    }

    public void deleteConsumerSetting(Long consumerId) {
        hashOps.delete(KEY, consumerId);
    }
}
