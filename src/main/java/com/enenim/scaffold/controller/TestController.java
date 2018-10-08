package com.enenim.scaffold.controller;

import com.enenim.scaffold.annotation.Get;
import com.enenim.scaffold.util.QueueEvent;
import com.enenim.scaffold.util.QueueEventPublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final QueueEventPublisherService queueEventPublisherService;

    @Autowired
    public TestController(QueueEventPublisherService queueEventPublisherService) {
        this.queueEventPublisherService = queueEventPublisherService;
    }

    @Get("/test/queue")
    public void getSubscribedEvents(){
        System.out.println("About to queue task");
        QueueEvent queueEvent = new QueueEvent();
        queueEventPublisherService.enQueue(queueEvent);
        System.out.println("Task queued");
    }
}
