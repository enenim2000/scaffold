package com.enenim.scaffold.controller;

import com.enenim.scaffold.annotation.*;
import com.enenim.scaffold.constant.AssetBaseConstant;
import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.constant.RouteConstant;
import com.enenim.scaffold.dto.request.ConsumerProfileRequest;
import com.enenim.scaffold.dto.request.ConsumerRequest;
import com.enenim.scaffold.dto.request.Request;
import com.enenim.scaffold.dto.request.SignUpVerifyRequest;
import com.enenim.scaffold.dto.response.*;
import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.enums.VerifyStatus;
import com.enenim.scaffold.exception.ScaffoldException;
import com.enenim.scaffold.model.dao.Consumer;
import com.enenim.scaffold.model.dao.Login;
import com.enenim.scaffold.service.FileStorageService;
import com.enenim.scaffold.service.MailSenderService;
import com.enenim.scaffold.service.cache.SharedExpireCacheService;
import com.enenim.scaffold.service.dao.ConsumerService;
import com.enenim.scaffold.service.dao.ConsumerSettingService;
import com.enenim.scaffold.service.dao.LoginService;
import com.enenim.scaffold.util.JsonConverter;
import com.enenim.scaffold.util.ObjectMapperUtil;
import com.enenim.scaffold.util.RequestUtil;
import com.enenim.scaffold.util.message.CommonMessage;
import com.enenim.scaffold.util.message.SpringMessage;
import com.enenim.scaffold.util.setting.ConsumerSystemSetting;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static com.enenim.scaffold.constant.RouteConstant.ADMINISTRATION_SETTING_SHOW;

@RestController
@RequestMapping("/user/consumers")
public class ConsumerController {

    private final ConsumerService consumerService;
    private final LoginService loginService;
    private final MailSenderService mailSenderService;
    private final FileStorageService fileStorageService;
    private final SharedExpireCacheService sharedExpireCacheService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConsumerSettingService consumerSettingService;

    public ConsumerController(ConsumerService consumerService, LoginService loginService, MailSenderService mailSenderService, FileStorageService fileStorageService, SharedExpireCacheService sharedExpireCacheService, BCryptPasswordEncoder bCryptPasswordEncoder, ConsumerSettingService consumerSettingService) {
        this.consumerService = consumerService;
        this.loginService = loginService;
        this.mailSenderService = mailSenderService;
        this.fileStorageService = fileStorageService;
        this.sharedExpireCacheService = sharedExpireCacheService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.consumerSettingService = consumerSettingService;
    }

    @Get
    @Role({RoleConstant.CONSUMER, RoleConstant.STAFF})
    @Permission(RouteConstant.USER_CONSUMER_INDEX)
    public Response<PageResponse<Consumer>> getConsumers() {
        return new Response<>(new PageResponse<>(consumerService.getConsumers()));
    }

    @Get("/{id}")
    @Role({RoleConstant.CONSUMER, RoleConstant.STAFF})
    @Permission(RouteConstant.USER_CONSUMER_SHOW)
    public Response<ModelResponse<Consumer>> showConsumer(@PathVariable Long id) {
        if(RequestUtil.getLogin().getUserType().equalsIgnoreCase(RoleConstant.CONSUMER)){
            id = RequestUtil.getLogin().getId();
        }
        return new Response<>(new ModelResponse<>(consumerService.getConsumer(id)));
    }

    @Post
    @Role({RoleConstant.STAFF})
    @Permission(RouteConstant.USER_CONSUMER_SIGNUP)
    public Response<ModelResponse<Consumer>> storeAnonymousConsumers(@Valid @RequestBody Request<ConsumerRequest> request){
        Consumer consumer = request.getBody().buildModel();
        consumer.setVerified(VerifyStatus.VERIFIED);
        consumer = consumerService.saveConsumer(consumer);

        //Save consumer settings to Consumer settings table
        consumerSettingService.saveConsumerSettings( consumerSettingService.getConsumerSettings(consumer) );

        return new Response<>(new ModelResponse<>(consumer));
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
            login.setPassword(bCryptPasswordEncoder.encode(request.getBody().getPassword()));
            login = loginService.saveLogin(login);
            if(!StringUtils.isEmpty(login.getId())){
                mailSenderService.send(consumer);
            }
            RequestUtil.setMessage(CommonMessage.msg("consumer_signup_success"));
            return new Response<>(new ModelResponse<>(consumer));
        }
        throw new ScaffoldException("signup_failed");
    }

    @Put("/{code}/verify")
    public Response<ModelResponse<Consumer>> verifyCode(@PathVariable("code") String code) throws Exception {
        String cacheCode = SharedExpireCacheService.SINGUP + SharedExpireCacheService.SEPARATOR + code;
        Object value = sharedExpireCacheService.get(cacheCode);
        if(!StringUtils.isEmpty(value)){
            Consumer consumer = JsonConverter.getObject(value, Consumer.class);
            consumer.setVerified(VerifyStatus.VERIFIED);
            consumer.setEnabled(EnabledStatus.ENABLED);
            consumer.skipAuthorization(true);
            consumer = consumerService.saveConsumer(consumer);

            //Save consumer settings to Consumer settings table
            consumerSettingService.saveConsumerSettings( consumerSettingService.getConsumerSettings(consumer) );

            loginService.updateVerifyStatus(VerifyStatus.VERIFIED, consumer.getEmail());
            sharedExpireCacheService.delete(cacheCode);
            RequestUtil.setMessage(CommonMessage.msg("consumer_code_verified"));

            return new Response<>(new ModelResponse<>(consumer));
        }
        throw new ScaffoldException("invalid_expired_code");
    }

    @Put("/{id}/details")
    public Response<ModelResponse<Consumer>> verifyCode(@PathVariable("id") Long id, @Valid @RequestBody Request<SignUpVerifyRequest> request) throws Exception {
        Consumer consumer = consumerService.getConsumer(id);
        Login login = loginService.getLoginByUsername(consumer.getEmail());
        if(StringUtils.isEmpty(login.getPassword())){
            if(StringUtils.isEmpty(request.getBody().getPassword())){
                throw new ScaffoldException("consumer_password_required");
            }
            login.setPassword(bCryptPasswordEncoder.encode(request.getBody().getPassword()));
        }
        login = loginService.saveLogin(login);
        if(!StringUtils.isEmpty(login)){
            consumer.setDateOfBirth(request.getBody().getDateOfBirth());
            consumer.setPhoneNumber(request.getBody().getPhoneNumber());
            consumer.setEnabled(EnabledStatus.ENABLED);
            consumer.skipAuthorization(true);
            consumer = consumerService.saveConsumer(consumer);
            return new Response<>(new ModelResponse<>(consumer));
        }
        throw new ScaffoldException("consumer_detail_failed");
    }

    @Post("/{email}/code/re-send")
    public Response<BooleanResponse> reSendCode(@PathVariable("email") String email){
        Consumer consumer = consumerService.getConsumerByEmail(email);
        if(!StringUtils.isEmpty(consumer)){
            if (consumer.getVerified() == VerifyStatus.NOT_VERIFIED){
                mailSenderService.send(consumer);
                RequestUtil.setMessage(CommonMessage.msg("consumer_code_resend"));
                return new Response<>(new BooleanResponse(true));
            }else if(consumer.getVerified() == VerifyStatus.VERIFIED){
                throw new ScaffoldException("verification_consumer_status");
            }
        }
        return new Response<>(new BooleanResponse(false));
    }

    @Put("/{id}/toggle")
    @Role({RoleConstant.STAFF})
    @Permission(RouteConstant.USER_CONSUMER_TOGGLE)
    public Response<StringResponse> toggle(@PathVariable Long id){
        return new Response<>(new StringResponse(consumerService.toggle(id)));
    }

    @Put("/{id}/profile")
    @Role({RoleConstant.STAFF, RoleConstant.CONSUMER})
    @Permission(RouteConstant.USER_CONSUMER_PROFILE)
    public Response<ModelResponse<ConsumerProfileResponse>> consumerProfile(@PathVariable("id") Long id, @RequestPart("profile") String profile, @RequestPart(value = "file", required = false) MultipartFile file){
        if(RequestUtil.getLogin().getUserType().equalsIgnoreCase(RoleConstant.CONSUMER)){
            id = RequestUtil.getLogin().getUserId();
        }
        Consumer consumer = consumerService.getConsumer(id);
        ConsumerProfileRequest cpr = JsonConverter.getObject(profile, ConsumerProfileRequest.class);
        consumer = ObjectMapperUtil.map(cpr, consumer);
        storeConsumerLogo(consumer, file);
        consumer = consumerService.saveConsumer(consumer);
        return new Response<>(new ModelResponse<>(JsonConverter.getObject(consumer, ConsumerProfileResponse.class)));
    }

    @Get("/{id}/settings/{key}")
    @Role({RoleConstant.STAFF, RoleConstant.CONSUMER})
    @Permission(ADMINISTRATION_SETTING_SHOW)
    public Response<ModelResponse<ConsumerSystemSetting>> getSetting(@PathVariable Long id, @PathVariable String key) {
        return new Response<>(new ModelResponse<>(consumerSettingService.getConsumerSystemSetting(id, key)));
    }

    @Get("/{id}/settings")
    @Role({RoleConstant.STAFF, RoleConstant.CONSUMER})
    @Permission(ADMINISTRATION_SETTING_SHOW)
    public Response<CollectionResponse<ConsumerSystemSetting>> getSettings(@PathVariable Long id) {
        return new Response<>(new CollectionResponse<>(consumerSettingService.getConsumerSystemSettings(id)));
    }

    private void storeConsumerLogo(Consumer consumer, MultipartFile file){
        if(!StringUtils.isEmpty(file)){
            long file_size = Long.valueOf(SpringMessage.msg("consumer_logo_upload_size"));
            if(file.getSize() > file_size){
                String file_size_kb = (file_size/1000) + "";
                throw new ScaffoldException("file_size_limit", file_size_kb,  HttpStatus.PAYLOAD_TOO_LARGE);
            }
            String fileName = fileStorageService.storeFile(file, "consumer-logo-" + consumer.getId() + ".jpg");
            consumer.setLogoUrl("/assets" + AssetBaseConstant.CONSUMER + fileName);
        }
    }
}
