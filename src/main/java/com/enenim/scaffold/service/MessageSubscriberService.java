package com.enenim.scaffold.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class MessageSubscriberService implements MessageListener {

    @Override
    public void onMessage(@NotNull Message message, @NotNull byte[] pattern) {
        /* TODO Convert byte i.e message.getBody() to QueueEvent and execute operation */
    }
}