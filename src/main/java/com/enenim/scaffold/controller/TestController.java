package com.enenim.scaffold.controller;

import com.enenim.scaffold.QueueEventTask;
import com.enenim.scaffold.annotation.Get;
import com.enenim.scaffold.service.QueueEventPublisherService;
import com.enenim.scaffold.util.JsonConverter;
import com.enenim.scaffold.util.QueueEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final QueueEventPublisherService queueEventPublisherService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public TestController(QueueEventPublisherService queueEventPublisherService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.queueEventPublisherService = queueEventPublisherService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Get("/test/password")
    public void generatePassword() throws ClassNotFoundException {
        System.out.println(" hash password = " + bCryptPasswordEncoder.encode("Password@123") );
    }

    @Get("/test/queue")
    public void getSubscribedEvents() throws ClassNotFoundException {
        for(int i=0; i<10; i++){
            QueueEvent queueEvent = new QueueEvent();
            queueEvent.setEventMethod("run");
            queueEvent.setEventType("email");
            queueEvent.setEventClass(QueueEventTask.class.getName());
            queueEvent.setEventTask(JsonConverter.getJson(new QueueEventTask(i)));
            queueEventPublisherService.enQueue(queueEvent);
        }
        System.out.println("All task executed");
    }
}
