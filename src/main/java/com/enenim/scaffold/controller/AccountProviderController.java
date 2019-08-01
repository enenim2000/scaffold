package com.enenim.scaffold.controller;

import com.enenim.scaffold.annotation.*;
import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.dto.request.AccountProviderRequest;
import com.enenim.scaffold.dto.request.Request;
import com.enenim.scaffold.dto.response.ModelResponse;
import com.enenim.scaffold.dto.response.PageResponse;
import com.enenim.scaffold.dto.response.Response;
import com.enenim.scaffold.model.dao.AccountProvider;
import com.enenim.scaffold.service.dao.AccountProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.enenim.scaffold.constant.RouteConstant.*;

@RestController
@RequestMapping("/account-providers")
public class AccountProviderController {

    private final AccountProviderService accountProviderService;

    @Autowired
    public AccountProviderController(AccountProviderService accountProviderService) {
        this.accountProviderService = accountProviderService;
    }

    @Get
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_ACCOUNT_PROVIDER_INDEX)
    public Response<PageResponse<AccountProvider>> getAccountProviders(){
        return new Response<>(new PageResponse<>(accountProviderService.getAccountProviders()));
    }

    @Get("/{id}")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_ACCOUNT_PROVIDER_SHOW)
    public Response<ModelResponse<AccountProvider>> getAccountProvider(@PathVariable Long id){
        return new Response<>(new ModelResponse<>(accountProviderService.getAccountProvider(id)));
    }

    @Post
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_ACCOUNT_PROVIDER_CREATE)
    public Response<ModelResponse<AccountProvider>> createAccountProvider(@Valid @RequestBody Request<AccountProviderRequest> request){
        return new Response<>(new ModelResponse<>(accountProviderService.saveAccountProvider(request.getBody().buildModel())));
    }

    @Put("/{id}")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_ACCOUNT_PROVIDER_UPDATE)
    public Response<ModelResponse<AccountProvider>> updateAccountProvider(@PathVariable Long id, @RequestBody Request<AccountProviderRequest> request){
        AccountProvider accountProvider = accountProviderService.getAccountProvider(id);
        return new Response<>(new ModelResponse<>(accountProviderService.saveAccountProvider(request.getBody().buildModel(accountProvider))));
    }

}
