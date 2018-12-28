package com.enenim.scaffold.config;

import com.enenim.scaffold.service.QueueEventSubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisQueueConfig {
    @Value("${redis.queue.host}")
    private String redisHost;

    @Value("${redis.queue.port}")
    private Integer redisPort;

    @Value("${redis.queue.password}")
    private String redisPassword;

    private final QueueEventSubscriberService messageSubscriberService;

    @Autowired
    public RedisQueueConfig(QueueEventSubscriberService messageSubscriberService) {
        this.messageSubscriberService = messageSubscriberService;
    }

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(messageSubscriberService);
    }

    @Bean
    RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container
                = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisQueueConnectionFactory());
        container.addMessageListener(messageListener(), topic());
        return container;
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("messageQueue");
    }

    @Bean(name = "redisQueueConnectionFactory")
    JedisConnectionFactory redisQueueConnectionFactory() {
        return RedisConfigUtil.getJedisConnectionFactory(redisHost, redisPort, redisPassword);
    }

    @Bean(name = "redisQueueTemplate")
    RedisTemplate<String, Object> redisQueueTemplate() throws Exception {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisQueueConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return template;
    }
}