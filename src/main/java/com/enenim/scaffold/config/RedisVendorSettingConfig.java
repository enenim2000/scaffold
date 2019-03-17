package com.enenim.scaffold.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisVendorSettingConfig {
    @Value("${redis.vendor_setting.host}")
    private String redisHost;

    @Value("${redis.vendor_setting.port}")
    private Integer redisPort;

    @Value("${redis.vendor_setting.password}")
    private String redisPassword;

    @Bean(name = "redisVendorSettingConnectionFactory")
    JedisConnectionFactory redisVendorSettingConnectionFactory() {
        return RedisConfigUtil.getJedisConnectionFactory(redisHost, redisPort, redisPassword);
    }

    @Bean(name = "redisVendorSettingTemplate")
    RedisTemplate<String, Object> redisVendorSettingTemplate() throws Exception {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisVendorSettingConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return template;
    }
}