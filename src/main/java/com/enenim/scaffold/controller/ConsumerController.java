package com.enenim.scaffold.controller;

import com.enenim.scaffold.annotation.Get;
import com.enenim.scaffold.annotation.Post;
import com.enenim.scaffold.annotation.Put;
import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.dto.request.BaseRequest;
import com.enenim.scaffold.dto.request.ConsumerRequest;
import com.enenim.scaffold.dto.response.BaseResponse;
import com.enenim.scaffold.dto.response.BooleanResponse;
import com.enenim.scaffold.dto.response.ModelResponse;
import com.enenim.scaffold.dto.response.PageResponse;
import com.enenim.scaffold.enums.VerifyStatus;
import com.enenim.scaffold.model.dao.Consumer;
import com.enenim.scaffold.model.dao.Login;
import com.enenim.scaffold.service.MailSenderService;
import com.enenim.scaffold.service.cache.SharedExpireCacheService;
import com.enenim.scaffold.service.dao.ConsumerService;
import com.enenim.scaffold.service.dao.LoginService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user/consumers")
public class ConsumerController {

    private final ConsumerService consumerService;
    private final LoginService loginService;
    private final MailSenderService mailSenderService;
    private final SharedExpireCacheService sharedExpireCacheService;

    public ConsumerController(ConsumerService consumerService, LoginService loginService, MailSenderService mailSenderService, SharedExpireCacheService sharedExpireCacheService) {
        this.consumerService = consumerService;
        this.loginService = loginService;
        this.mailSenderService = mailSenderService;
        this.sharedExpireCacheService = sharedExpireCacheService;
    }

    @Get
    public BaseResponse<PageResponse<Consumer>> getConsumers() {
        return new BaseResponse<>(new PageResponse<>(consumerService.getConsumers()));
    }

    @Get("/{id}")
    public BaseResponse<ModelResponse<Consumer>> showConsumer(@PathVariable Long id) {
        return new BaseResponse<>(new ModelResponse<>(consumerService.getConsumer(id)));
    }

    @Post
    public BaseResponse<ModelResponse<Consumer>> storeAnonymousConsumers(@Valid @RequestBody BaseRequest<ConsumerRequest> baseRequest){
        return new BaseResponse<>(new ModelResponse<>(consumerService.saveConsumer(baseRequest.getData().validateRequest())));
    }

    @Post("/sign-up")
    public BaseResponse<ModelResponse<Consumer>> signUpConsumers(@Valid @RequestBody BaseRequest<ConsumerRequest> baseRequest){
        Consumer consumer = consumerService.saveConsumer(baseRequest.getData().validateRequest());
        if(!StringUtils.isEmpty(consumer)){
            Login login = new Login();
            login.setUsername(consumer.getEmail());
            login.setUserType(RoleConstant.CONSUMER);
            login.setUserId(consumer.getId());
            login = loginService.saveLogin(login);
            if(!StringUtils.isEmpty(login.getId())){
                mailSenderService.send(consumer);
            }
        }
        return new BaseResponse<>(new ModelResponse<>(consumer));
    }

    @Post("/{code}/verify")
    public BaseResponse<ModelResponse<Consumer>> verifyCode(@PathVariable("code") String code){
        Object value = sharedExpireCacheService.get(code);
        Consumer consumer = null;
        if(!StringUtils.isEmpty(value)){
            consumer = (Consumer)value;
            Login login = loginService.getLoginByUsername(consumer.getEmail());
            if(!StringUtils.isEmpty(login)){
                consumer.setVerified(VerifyStatus.VERIFIED);
                consumerService.saveConsumer(consumer);
            }
        }
        return new BaseResponse<>(new ModelResponse<>(consumer));
    }

    @Post("/{email}/code/re-send")
    public BaseResponse<BooleanResponse> reSendCode(@PathVariable("email") String email){
        return null;//new BaseResponse<>(new BooleanResponse<>(consumer));
    }

    @Put("/{id}/toggle")
    public BaseResponse<BooleanResponse> toggle(@PathVariable Long id){
        return  new BaseResponse<>(new BooleanResponse(consumerService.toggle(id)));
    }
}
