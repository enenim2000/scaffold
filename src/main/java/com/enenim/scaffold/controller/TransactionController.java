package com.enenim.scaffold.controller;

import com.enenim.scaffold.service.dao.ConsumerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final ConsumerService consumerService;


    public TransactionController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

}
