package com.enenim.scaffold.config;

import com.enenim.scaffold.model.cache.SettingCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisSettingConfig {
    @Value("${redis.setting.host}")
    private String redisHost;

    @Value("${redis.setting.port}")
    private Integer redisPort;

    @Value("${redis.setting.password}")
    private String redisPassword;

    @Bean(name = "redisSettingConnectionFactory")
    JedisConnectionFactory redisSettingConnectionFactory() {
        return RedisConfigUtil.getJedisConnectionFactory(redisHost, redisPort, redisPassword);
    }

    @Bean(name = "redisSettingTemplate")
    RedisTemplate<String, SettingCache> redisSettingTemplate() throws Exception {
        final RedisTemplate<String, SettingCache> template = new RedisTemplate<>();
        template.setConnectionFactory(redisSettingConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return template;
    }
}