package com.enenim.scaffold.interfaces;

import com.enenim.scaffold.util.QueueEvent;

public interface QueueEventPublisher {
    void enQueue(QueueEvent queueEvent);
}