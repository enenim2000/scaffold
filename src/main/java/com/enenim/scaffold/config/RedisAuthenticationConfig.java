package com.enenim.scaffold.config;

import com.enenim.scaffold.model.cache.LoginCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Map;

@Configuration
public class RedisAuthenticationConfig {
    @Value("${redis.authentication.host}")
    private String redisHost;

    @Value("${redis.authentication.port}")
    private Integer redisPort;

    @Value("${redis.authentication.password}")
    private String redisPassword;

    @Bean(name = "redisAuthenticationConnectionFactory")
    @Primary
    JedisConnectionFactory redisAuthenticationConnectionFactory() {
        return RedisConfigUtil.getJedisConnectionFactory(redisHost, redisPort, redisPassword);
    }

    /**
     * RedisTemplate<Long, Map<String, LoginCache>> => RedisTemplate<id, Map<sessionId, LoginCache>>
     */
    @Bean(name = "redisAuthenticationTemplate")
    RedisTemplate<String, Map<String, LoginCache>> redisAuthenticationTemplate() throws Exception {
        final RedisTemplate<String, Map<String, LoginCache>> template = new RedisTemplate<>();
        template.setConnectionFactory(redisAuthenticationConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return template;
    }
}