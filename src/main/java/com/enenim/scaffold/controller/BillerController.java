package com.enenim.scaffold.controller;

import com.enenim.scaffold.annotation.*;
import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.constant.RouteConstant;
import com.enenim.scaffold.dto.request.BillerRequest;
import com.enenim.scaffold.dto.request.ConsumerRequest;
import com.enenim.scaffold.dto.request.Request;
import com.enenim.scaffold.dto.request.SignUpVerifyRequest;
import com.enenim.scaffold.dto.response.BooleanResponse;
import com.enenim.scaffold.dto.response.ModelResponse;
import com.enenim.scaffold.dto.response.PageResponse;
import com.enenim.scaffold.dto.response.Response;
import com.enenim.scaffold.enums.VerifyStatus;
import com.enenim.scaffold.exception.ScaffoldException;
import com.enenim.scaffold.exception.UnAuthorizedException;
import com.enenim.scaffold.model.dao.Biller;
import com.enenim.scaffold.model.dao.Consumer;
import com.enenim.scaffold.model.dao.Login;
import com.enenim.scaffold.service.MailSenderService;
import com.enenim.scaffold.service.cache.SharedExpireCacheService;
import com.enenim.scaffold.service.dao.BillerService;
import com.enenim.scaffold.service.dao.LoginService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/user/billers")
public class BillerController {

    private final BillerService billerService;
    private final LoginService loginService;
    private final MailSenderService mailSenderService;
    private final SharedExpireCacheService sharedExpireCacheService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public BillerController(BillerService billerService, LoginService loginService, MailSenderService mailSenderService, SharedExpireCacheService sharedExpireCacheService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.billerService = billerService;
        this.loginService = loginService;
        this.mailSenderService = mailSenderService;
        this.sharedExpireCacheService = sharedExpireCacheService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Get
    @Role({RoleConstant.BILLER, RoleConstant.STAFF})
    @Permission(RouteConstant.USER_BILLER_INDEX)
    public Response<PageResponse<Biller>> getBillers() {
        return new Response<>(new PageResponse<>(billerService.getBillers()));
    }

    @Get("/{id}")
    @Role({RoleConstant.BILLER, RoleConstant.STAFF})
    @Permission(RouteConstant.USER_BILLER_SHOW)
    public Response<ModelResponse<Biller>> showConsumer(@PathVariable Long id) {
        return new Response<>(new ModelResponse<>(billerService.getBiller(id)));
    }

    @Post
    @Role({RoleConstant.STAFF})
    @Permission(RouteConstant.USER_BILLER_CREATE)
    public Response<ModelResponse<Biller>> createBiller(@Valid @RequestBody Request<BillerRequest> request){
        Biller biller = request.getBody().buildModel();
        biller.setTestSecret(bCryptPasswordEncoder.encode(new Date().toString() + Math.random()));
        biller.setSecret(bCryptPasswordEncoder.encode(new Date().toString() + Math.random()));
        return new Response<>(new ModelResponse<>(billerService.saveBiller(biller)));
    }

    @Post("/sign-up")
    public Response<ModelResponse<Consumer>> signUpConsumers(@Valid @RequestBody Request<ConsumerRequest> request){
        consumerService.validateDependencies(request.getBody());
        Consumer consumer = request.getBody().buildModel();
        consumer.skipAuthorization(true);
        consumer = consumerService.saveConsumer(consumer);
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

    @Put("/{code}/verify")
    public Response<ModelResponse<Consumer>> verifyCode(@PathVariable("code") String code, @Valid @RequestBody SignUpVerifyRequest request) throws Exception {
        Object value = sharedExpireCacheService.get(code);
        if(!StringUtils.isEmpty(value)){
            Consumer consumer = (Consumer)value;
            Login login = loginService.getLoginByUsername(consumer.getEmail());
            login.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
            login = loginService.saveLogin(login);
            if(!StringUtils.isEmpty(login)){
                consumer.setVerified(VerifyStatus.VERIFIED);
                consumer.skipAuthorization(true);
                consumer = consumerService.saveConsumer(consumer);
                sharedExpireCacheService.delete(code);
                return new Response<>(new ModelResponse<>(consumer));
            }
        }
        throw new UnAuthorizedException("invalid_expired_code");
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
    @Role({RoleConstant.STAFF})
    @Permission(RouteConstant.USER_CONSUMER_TOGGLE)
    public Response<BooleanResponse> toggle(@PathVariable Long id){
        return new Response<>(new BooleanResponse(consumerService.toggle(id)));
    }
}
