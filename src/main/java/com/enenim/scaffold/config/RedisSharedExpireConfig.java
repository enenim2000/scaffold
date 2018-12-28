package com.enenim.scaffold.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisSharedExpireConfig {
    @Value("${redis.sharedexpire.host}")
    private String redisHost;

    @Value("${redis.sharedexpire.port}")
    private Integer redisPort;

    @Value("${redis.sharedexpire.password}")
    private String redisPassword;

    @Bean(name = "redisSharedExpireConnectionFactory")
    JedisConnectionFactory redisSharedExpireConnectionFactory() {
        return RedisConfigUtil.getJedisConnectionFactory(redisHost, redisPort, redisPassword);
    }

    @Bean(name = "redisSharedExpireTemplate")
    RedisTemplate<String, Object> redisSharedExpireTemplate() throws Exception {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisSharedExpireConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return template;
    }
}