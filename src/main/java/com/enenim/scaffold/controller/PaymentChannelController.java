package com.enenim.scaffold.controller;

import com.enenim.scaffold.annotation.*;
import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.dto.request.PaymentChannelRequest;
import com.enenim.scaffold.dto.request.Request;
import com.enenim.scaffold.dto.response.ModelResponse;
import com.enenim.scaffold.dto.response.PageResponse;
import com.enenim.scaffold.dto.response.Response;
import com.enenim.scaffold.model.dao.PaymentChannel;
import com.enenim.scaffold.service.dao.PaymentChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.enenim.scaffold.constant.RouteConstant.*;

@RestController
@RequestMapping("/payment-channels")
public class PaymentChannelController {

    private final PaymentChannelService paymentChannelService;

    @Autowired
    public PaymentChannelController(PaymentChannelService paymentChannelService) {
        this.paymentChannelService = paymentChannelService;
    }

    @Get
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_PAYMENT_CHANNEL_INDEX)
    public Response<PageResponse<PaymentChannel>> getPaymentChannels(){
        return new Response<>(new PageResponse<>(paymentChannelService.getPaymentChannels()));
    }

    @Get("/{id}")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_PAYMENT_CHANNEL_SHOW)
    public Response<ModelResponse<PaymentChannel>> getPaymentChannel(@PathVariable Long id){
        return new Response<>(new ModelResponse<>(paymentChannelService.getPaymentChannel(id)));
    }

    @Post
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_PAYMENT_CHANNEL_CREATE)
    public Response<ModelResponse<PaymentChannel>> createPaymentChannel(@Valid @RequestBody Request<PaymentChannelRequest> request){
        return new Response<>(new ModelResponse<>(paymentChannelService.savePaymentChannel(request.getBody().buildModel())));
    }

    @Put("/{id}")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_PAYMENT_CHANNEL_UPDATE)
    public Response<ModelResponse<PaymentChannel>> updatePaymentChannel(@PathVariable Long id, @RequestBody Request<PaymentChannelRequest> request){
        PaymentChannel paymentChannel = paymentChannelService.getPaymentChannel(id);
        return new Response<>(new ModelResponse<>(paymentChannelService.savePaymentChannel(request.getBody().buildModel(paymentChannel))));
    }

}
