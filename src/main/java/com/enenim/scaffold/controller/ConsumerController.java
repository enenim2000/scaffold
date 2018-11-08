package com.enenim.scaffold.controller;

import com.enenim.scaffold.annotation.Get;
import com.enenim.scaffold.annotation.Post;
import com.enenim.scaffold.annotation.Put;
import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.dto.request.Request;
import com.enenim.scaffold.dto.request.ConsumerRequest;
import com.enenim.scaffold.dto.response.Response;
import com.enenim.scaffold.dto.response.BooleanResponse;
import com.enenim.scaffold.dto.response.ModelResponse;
import com.enenim.scaffold.dto.response.PageResponse;
import com.enenim.scaffold.enums.VerifyStatus;
import com.enenim.scaffold.exception.ScaffoldException;
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
    public Response<PageResponse<Consumer>> getConsumers() {
        return new Response<>(new PageResponse<>(consumerService.getConsumers()));
    }

    @Get("/{id}")
    public Response<ModelResponse<Consumer>> showConsumer(@PathVariable Long id) {
        return new Response<>(new ModelResponse<>(consumerService.getConsumer(id)));
    }

    @Post
    public Response<ModelResponse<Consumer>> storeAnonymousConsumers(@RequestBody Request<ConsumerRequest> request){
        request.getBody().validateRequest();
        return new Response<>(new ModelResponse<>(consumerService.saveConsumer(request.getBody().buildModel())));
    }

    @Post("/sign-up")
    public Response<ModelResponse<Consumer>> signUpConsumers(@RequestBody Request<ConsumerRequest> request){
        request.getBody().validateRequest();
        consumerService.validateDependencies(request.getBody());
        Consumer consumer = consumerService.saveConsumer(request.getBody().buildModel());
        if(!StringUtils.isEmpty(consumer)){
            Login login = new Login();
            login.setUsername(consumer.getEmail());
            login.setUserType(RoleConstant.CONSUMER);
            login.setUserId(consumer.getId());
            login = loginService.saveLogin(login);
            if(!StringUtils.isEmpty(login.getId())){
                mailSenderService.send(consumer);
            }
            return new Response<>(new ModelResponse<>(consumer));
        }
        throw new ScaffoldException("signup_failed");
    }

    @Post("/{code}/verify")
    public Response<ModelResponse<Consumer>> verifyCode(@PathVariable("code") String code) throws Exception {
        Object value = sharedExpireCacheService.get(code);
        if(!StringUtils.isEmpty(value)){
            Consumer consumer = (Consumer)value;
            Login login = loginService.getLoginByUsername(consumer.getEmail());
            if(!StringUtils.isEmpty(login)){
                consumer.setVerified(VerifyStatus.VERIFIED);
                consumerService.saveConsumer(consumer);
                sharedExpireCacheService.delete(code);
                return new Response<>(new ModelResponse<>(consumer));
            }
        }
        throw new Exception("Expired/Invalid code");
    }

    @Post("/{email}/code/re-send")
    public Response<BooleanResponse> reSendCode(@PathVariable("email") String email){
        Consumer consumer = consumerService.getConsumerByEmail(email);
        if(!StringUtils.isEmpty(consumer)){
            if (consumer.getVerified() == VerifyStatus.NOT_VERIFIED){
                mailSenderService.send(consumer);
                return new Response<>(new BooleanResponse(true));
            }
        }
        return new Response<>(new BooleanResponse(false));
    }

    @Put("/{id}/toggle")
    public Response<BooleanResponse> toggle(@PathVariable Long id){
        return new Response<>(new BooleanResponse(consumerService.toggle(id)));
    }
}
