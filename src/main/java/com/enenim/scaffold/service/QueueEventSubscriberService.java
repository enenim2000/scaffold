package com.enenim.scaffold.service;

import com.enenim.scaffold.QueueEventTask;
import com.enenim.scaffold.util.JsonConverter;
import com.enenim.scaffold.util.QueueEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class QueueEventSubscriberService implements MessageListener {

    @Override
    public void onMessage(@NotNull Message message, @NotNull byte[] pattern) {
        String json = new String (message.getBody());
        QueueEvent queueEvent = JsonConverter.getElement(json, QueueEvent.class);
        try {
            if(Class.forName(queueEvent.getEventClass()).equals(QueueEventTask.class)){
                JsonConverter.getElement(queueEvent.getEventTask(), QueueEventTask.class).execute();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}