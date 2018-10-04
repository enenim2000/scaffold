package com.enenim.scaffold.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisSharedConfig {
    @Value("${redis.shared.host}")
    private String redisHost;

    @Value("${redis.shared.port}")
    private Integer redisPort;

    @Value("${redis.shared.password}")
    private String redisPassword;

    @Bean(name = "redisSharedConnectionFactory")
    JedisConnectionFactory redisSharedConnectionFactory() {
        return RedisConfigUtil.getJedisConnectionFactory(redisHost, redisPort, redisPassword);
    }

    @Bean(name = "redisSharedTemplate")
    RedisTemplate<String, Object> redisSharedTemplate() throws Exception {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisSharedConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return template;
    }
}