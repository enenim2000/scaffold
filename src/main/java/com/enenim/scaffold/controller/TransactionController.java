package com.enenim.scaffold.controller;

import com.enenim.scaffold.annotation.Get;
import com.enenim.scaffold.annotation.Post;
import com.enenim.scaffold.annotation.Role;
import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.dto.request.Request;
import com.enenim.scaffold.dto.request.ServiceRequest;
import com.enenim.scaffold.dto.request.TransactionRequest;
import com.enenim.scaffold.dto.response.ModelResponse;
import com.enenim.scaffold.dto.response.PageResponse;
import com.enenim.scaffold.dto.response.Response;
import com.enenim.scaffold.enums.Initiator;
import com.enenim.scaffold.model.dao.Service;
import com.enenim.scaffold.model.dao.ServiceForm;
import com.enenim.scaffold.model.dao.Transaction;
import com.enenim.scaffold.service.dao.ConsumerService;
import com.enenim.scaffold.service.dao.TransactionService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

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

    @Post
    @Role({RoleConstant.STAFF, RoleConstant.CONSUMER})
    public Response<ModelResponse<Transaction>> createTransaction(@Valid @RequestBody Request<TransactionRequest> request){
        Transaction transaction = new Transaction();
        TransactionRequest transactionRequest = request.getBody();
        transaction.setBranch(null);
        transaction.setCurrency(null);
        transaction.setPaymentChannel(null);
        transaction.setPaymentMethod(null);
        transaction.setStaff(null);
        transaction.setInitiator(Initiator.USER);
        transaction.setConsumer(consumerService.getConsumer(transactionRequest.getConsumerId()));

        Set<ServiceForm> serviceForms = new HashSet<>();
        for(ServiceRequest serviceRequest : transactionRequest.getServices()){
            ServiceForm serviceForm = new ServiceForm();
            Service service = new Service();
            service.setId(serviceRequest.getServiceId());
            serviceForm.setPayload(serviceRequest.getConsumerForm());
            serviceForm.setService(service);
            serviceForms.add(serviceForm);
        }
        transaction.setServiceForms(serviceForms);

        return new Response<>(new ModelResponse<>(transactionService.saveTransaction(transaction)));
    }

}
