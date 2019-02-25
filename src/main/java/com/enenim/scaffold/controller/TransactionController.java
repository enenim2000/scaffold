package com.enenim.scaffold.controller;

import com.enenim.scaffold.annotation.Get;
import com.enenim.scaffold.annotation.Role;
import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.dto.response.ModelResponse;
import com.enenim.scaffold.dto.response.PageResponse;
import com.enenim.scaffold.dto.response.Response;
import com.enenim.scaffold.model.dao.Transaction;
import com.enenim.scaffold.service.dao.ConsumerService;
import com.enenim.scaffold.service.dao.TransactionService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final ConsumerService consumerService;
    private final TransactionService transactionService;

    public TransactionController(ConsumerService consumerService, TransactionService transactionService) {
        this.consumerService = consumerService;
        this.transactionService = transactionService;
    }

    @Get
    @Role({RoleConstant.CONSUMER, RoleConstant.STAFF})
    public Response<PageResponse<Transaction>> getTransactions(){
        return new Response<>(new PageResponse<>(transactionService.getTransactions()));
    }

    @Get("/{id}")
    @Role({RoleConstant.STAFF})
    public Response<ModelResponse<Transaction>> getTransaction(@PathVariable Long id){
        return new Response<>(new ModelResponse<>(transactionService.getTransaction(id)));
    }

    /*@Post
    @Role({RoleConstant.STAFF, RoleConstant.CONSUMER})
    public Response<ModelResponse<Branch>> createTransaction(@Valid @RequestBody Request<TransactionRequest> request){
        //RequestUtil.setCommonRequestProperties(request);

    }*/

}
