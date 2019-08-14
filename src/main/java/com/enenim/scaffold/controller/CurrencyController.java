package com.enenim.scaffold.controller;

import com.enenim.scaffold.annotation.*;
import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.dto.request.CurrencyRequest;
import com.enenim.scaffold.dto.response.ModelResponse;
import com.enenim.scaffold.dto.response.PageResponse;
import com.enenim.scaffold.dto.response.Response;
import com.enenim.scaffold.model.dao.Currency;
import com.enenim.scaffold.service.dao.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.enenim.scaffold.constant.RouteConstant.*;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Get
    @Role({RoleConstant.STAFF, RoleConstant.CONSUMER})
    @Permission(ADMINISTRATION_CURRENCY_INDEX)
    public Response<PageResponse<Currency>> getCurrencies(){
        return new Response<>(new PageResponse<>(currencyService.getCurrencies()));
    }

    @Get("/{id}")
    @Role({RoleConstant.STAFF, RoleConstant.CONSUMER})
    @Permission(ADMINISTRATION_CURRENCY_SHOW)
    public Response<ModelResponse<Currency>> getCurrency(@PathVariable Long id){
        return new Response<>(new ModelResponse<>(currencyService.getCurrency(id)));
    }

    @Post
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_CURRENCY_CREATE)
    public Response<ModelResponse<Currency>> createCurrency(@Valid @RequestBody CurrencyRequest request){
        return new Response<>(new ModelResponse<>(currencyService.saveCurrency(request.buildModel())));
    }

    @Put("/{id}")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_CURRENCY_UPDATE)
    public Response<ModelResponse<Currency>> updateCurrency(@PathVariable Long id, @RequestBody CurrencyRequest request){
        Currency currency = currencyService.getCurrency(id);
        return new Response<>(new ModelResponse<>(currencyService.saveCurrency(request.buildModel(currency))));
    }

}
