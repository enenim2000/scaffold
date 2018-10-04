package com.enenim.scaffold.util;

import com.enenim.scaffold.interfaces.QueueEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class QueueEventPublisherService implements QueueEventPublisher {

    @Qualifier(value = "redisQueueTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    private ChannelTopic topic;
 
    public QueueEventPublisherService() {
    }

    @Autowired
    public QueueEventPublisherService(@Qualifier("redisQueueTemplate") RedisTemplate<String, Object> redisTemplate, ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.topic = topic;
    }

    @Override
    public void enQueue(QueueEvent queueEvent) {
        redisTemplate.convertAndSend(topic.getTopic(), queueEvent);
    }
}