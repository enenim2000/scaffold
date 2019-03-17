package com.enenim.scaffold.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConsumerSettingConfig {
    @Value("${redis.consumer_setting.host}")
    private String redisHost;

    @Value("${redis.consumer_setting.port}")
    private Integer redisPort;

    @Value("${redis.consumer_setting.password}")
    private String redisPassword;

    @Bean(name = "redisConsumerSettingConnectionFactory")
    JedisConnectionFactory redisConsumerSettingConnectionFactory() {
        return RedisConfigUtil.getJedisConnectionFactory(redisHost, redisPort, redisPassword);
    }

    @Bean(name = "redisConsumerSettingTemplate")
    RedisTemplate<String, Object> redisConsumerSettingTemplate() throws Exception {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConsumerSettingConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return template;
    }
}