package com.enenim.scaffold.service.cache;

import com.enenim.scaffold.util.JsonConverter;
import com.enenim.scaffold.util.setting.ConsumerSystemSetting;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
public class ConsumerSettingCacheService {

    @Qualifier(value = "redisConsumerSettingTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    private HashOperations<String, String, Object> hashOps;

    private final static String KEY = "Consumer";

    public ConsumerSettingCacheService(){}

    @Autowired
    private ConsumerSettingCacheService(@Qualifier("redisConsumerSettingTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOps = redisTemplate.opsForHash();
    }

    public List<ConsumerSystemSetting> getConsumerSetting(Long consumerId) {
        String json = (String) hashOps.get(KEY, "list:" + consumerId);

        try {
            return new ObjectMapper().readValue(json, new TypeReference<List<ConsumerSystemSetting>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public HashMap<String, ConsumerSystemSetting> getConsumerSettingMap(Long consumerId) {
        String json = (String) hashOps.get(KEY, "map:" + consumerId);

        try {
            return new ObjectMapper().readValue(json, new TypeReference<HashMap<String, ConsumerSystemSetting>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void saveConsumerSetting(Long consumerId, List<ConsumerSystemSetting> consumerSettings) {
        hashOps.put(KEY, "list:" + String.valueOf(consumerId), JsonConverter.getJson(consumerSettings));
    }

    public void saveConsumerSetting(Long consumerId, HashMap<String, ConsumerSystemSetting> consumerSettings) {
        hashOps.put(KEY, "map:" + String.valueOf(consumerId), JsonConverter.getJson(consumerSettings));
    }

    public void deleteConsumerSetting(Long consumerId) {
        hashOps.delete(KEY, consumerId);
    }
}