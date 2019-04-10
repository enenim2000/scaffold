package com.enenim.scaffold.controller;

import com.enenim.scaffold.annotation.*;
import com.enenim.scaffold.constant.AssetBaseConstant;
import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.constant.RouteConstant;
import com.enenim.scaffold.dto.request.ConsumerProfileRequest;
import com.enenim.scaffold.dto.request.ConsumerRequest;
import com.enenim.scaffold.dto.request.Request;
import com.enenim.scaffold.dto.request.part.TransactionFilterRequest;
import com.enenim.scaffold.dto.response.*;
import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.enums.TicketStatus;
import com.enenim.scaffold.enums.VerifyStatus;
import com.enenim.scaffold.exception.ScaffoldException;
import com.enenim.scaffold.model.dao.Consumer;
import com.enenim.scaffold.model.dao.Login;
import com.enenim.scaffold.model.dao.Transaction;
import com.enenim.scaffold.service.FileStorageService;
import com.enenim.scaffold.service.MailSenderService;
import com.enenim.scaffold.service.UserResolverService;
import com.enenim.scaffold.service.cache.SharedExpireCacheService;
import com.enenim.scaffold.service.dao.*;
import com.enenim.scaffold.util.JsonConverter;
import com.enenim.scaffold.util.ObjectMapperUtil;
import com.enenim.scaffold.util.PasswordEncoder;
import com.enenim.scaffold.util.RequestUtil;
import com.enenim.scaffold.util.message.CommonMessage;
import com.enenim.scaffold.util.message.SpringMessage;
import com.enenim.scaffold.util.setting.ConsumerSystemSetting;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Optional;

import static com.enenim.scaffold.constant.RouteConstant.*;

@RestController
@RequestMapping("/user/consumers")
public class ConsumerController {

    private final ConsumerService consumerService;
    private final LoginService loginService;
    private final MailSenderService mailSenderService;
    private final FileStorageService fileStorageService;
    private final SharedExpireCacheService sharedExpireCacheService;
    private final PasswordEncoder passwordEncoder;
    private final ConsumerSettingService consumerSettingService;
    private final UserResolverService userResolverService;
    private final TransactionService transactionService;
    private final TicketService ticketService;
    private final FeedbackService feedbackService;

    public ConsumerController(ConsumerService consumerService, LoginService loginService, MailSenderService mailSenderService, FileStorageService fileStorageService, SharedExpireCacheService sharedExpireCacheService, PasswordEncoder passwordEncoder, ConsumerSettingService consumerSettingService, UserResolverService userResolverService, TransactionService transactionService, TicketService ticketService, FeedbackService feedbackService) {
        this.consumerService = consumerService;
        this.loginService = loginService;
        this.mailSenderService = mailSenderService;
        this.fileStorageService = fileStorageService;
        this.sharedExpireCacheService = sharedExpireCacheService;
        this.passwordEncoder = passwordEncoder;
        this.consumerSettingService = consumerSettingService;
        this.userResolverService = userResolverService;
        this.transactionService = transactionService;
        this.ticketService = ticketService;
        this.feedbackService = feedbackService;
    }

    @Get
    @Role({RoleConstant.STAFF})
    @Permission(RouteConstant.USER_CONSUMER_INDEX)
    public Response<PageResponse<Consumer>> getConsumers() {
        return new Response<>(new PageResponse<>(consumerService.getConsumers()));
    }

    @Get("/{id}")
    @Role({RoleConstant.CONSUMER, RoleConstant.STAFF})
    @Permission(RouteConstant.USER_CONSUMER_SHOW)
    public Response<ModelResponse<Consumer>> showConsumer(@PathVariable Long id) {
        id = userResolverService.resolveUserId(id);
        return new Response<>(new ModelResponse<>(consumerService.getConsumer(id)));
    }

    @Post("/sign-up")
    public Response<ModelResponse<Consumer>> signUpConsumers(@Valid @RequestBody Request<ConsumerRequest> request){

        if(!request.getBody().getPassword().equals(request.getBody().getConfirmPassword())){
            throw new ScaffoldException("password_mismatch");
        }

        consumerService.validateDependencies(request.getBody());
        Consumer consumer = request.getBody().buildModel();
        consumer.skipAuthorization(true);
        consumer = consumerService.saveConsumer(consumer);
        if(!StringUtils.isEmpty(consumer)){
            Login login = new Login();
            login.setUsername(consumer.getEmail());
            login.setUserType(RoleConstant.CONSUMER);
            login.setUserId(consumer.getId());
            login.setPassword(passwordEncoder.encode(request.getBody().getPassword()));
            login = loginService.saveLogin(login);
            if(!StringUtils.isEmpty(login.getId())){
                System.out.println("about to send mail " + login);
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
            consumerSettingService.saveConsumerSettings( consumerSettingService.getConsumerSettings(consumer) );
            loginService.updateVerifyStatus(VerifyStatus.VERIFIED, consumer.getEmail());
            sharedExpireCacheService.delete(cacheCode);
            RequestUtil.setMessage(CommonMessage.msg("consumer_code_verified"));
            return new Response<>(new ModelResponse<>(consumer));
        }
        throw new ScaffoldException("invalid_expired_code");
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

    @Put("/{id}/profile")
    @Role({RoleConstant.STAFF, RoleConstant.CONSUMER})
    @Permission(RouteConstant.USER_CONSUMER_PROFILE)
    public Response<ModelResponse<ConsumerProfileResponse>> consumerProfile(@PathVariable("id") Long id, @RequestPart("profile") String profile, @RequestPart(value = "file", required = false) MultipartFile file){
        id = userResolverService.resolveUserId(id);
        Consumer consumer = consumerService.getConsumer(id);
        ConsumerProfileRequest cpr = JsonConverter.getObject(profile, ConsumerProfileRequest.class);
        consumer = ObjectMapperUtil.map(cpr, consumer);
        consumer.skipAuthorization(true);
        storeConsumerLogo(consumer, file);
        consumer = consumerService.saveConsumer(consumer);
        return new Response<>(new ModelResponse<>(JsonConverter.getObject(consumer, ConsumerProfileResponse.class)));
    }

    @Get("/{id}/settings/{key}")
    @Role({RoleConstant.STAFF, RoleConstant.CONSUMER})
    @Permission(USER_CONSUMER_SETTING_SHOW)
    public Response<ModelResponse<ConsumerSystemSetting>> getSetting(@PathVariable Long id, @PathVariable String key) {
        return new Response<>(new ModelResponse<>(consumerSettingService.getConsumerSystemSetting(id, key)));
    }

    @Get("/{id}/settings")
    @Role({RoleConstant.STAFF, RoleConstant.CONSUMER})
    @Permission(USER_CONSUMER_SETTING_INDEX)
    public Response<CollectionResponse<ConsumerSystemSetting>> getSettings(@PathVariable Long id) {
        return new Response<>(new CollectionResponse<>(consumerSettingService.getConsumerSystemSettings(id)));
    }

    @Put("/{id}/settings/sync")
    @Role({RoleConstant.STAFF})
    @Permission(USER_CONSUMER_SETTING_SYNC)
    public Response<BooleanResponse> syncConsumerSettings(@PathVariable("id") Long id) {
        return new Response<>(new BooleanResponse(consumerSettingService.syncConsumerSettings(id)));
    }

    @Get({"/{id}/transactions"})
    @Role({RoleConstant.STAFF, RoleConstant.CONSUMER})
    @Permission(USER_CONSUMER_TRANSACTION_INDEX)
    public Response<PageResponse<Transaction>> getConsumerTransactions(@PathVariable Long id, @Valid @RequestBody TransactionFilterRequest filter) {
        return new Response<>(new PageResponse<>(transactionService.getConsumerTransactions(filter, userResolverService.resolveUserId(id))));
    }

    @Get({"/{id}/tickets", "/{id}/tickets/{status}"})
    @Role({RoleConstant.STAFF, RoleConstant.CONSUMER})
    @Permission(USER_CONSUMER_TICKET_INDEX)
    public Response<PageResponse<TicketResponse>> getConsumerTickets(@PathVariable Long id, @PathVariable("status") Optional<TicketStatus> status) {
        id = userResolverService.resolveUserId(id);
        if(status.isPresent()){
            return new Response<>(new PageResponse<>(ticketService.getTickets(id, status.get())));
        }else {
            return new Response<>(new PageResponse<>(ticketService.getTickets(id)));
        }
    }

    @Get("/{id}/tickets/{ticket-id}")
    @Role({RoleConstant.STAFF, RoleConstant.CONSUMER})
    @Permission(USER_CONSUMER_TICKET_SHOW)
    public Response<ModelResponse<TicketResponse>> getConsumerTicket(@PathVariable Long id, @PathVariable("ticket-id") Long ticketId) {
        return new Response<>(new ModelResponse<>(
                ObjectMapperUtil.map(ticketService.getConsumerTicket(userResolverService.resolveUserId(id), ticketId), TicketResponse.class)));
    }

    @Get("/{id}/feedback")
    @Role({RoleConstant.STAFF, RoleConstant.CONSUMER})
    @Permission(USER_CONSUMER_FEEDBACK_INDEX)
    public Response<PageResponse<FeedbackResponse>> getConsumerFeedback(@PathVariable Long id) {
        return new Response<>(new PageResponse<>(feedbackService.getConsumerFeedbackResponses(userResolverService.resolveUserId(id))));
    }

    @Get("/{id}/feedback/{feedback-id}")
    @Role({RoleConstant.STAFF, RoleConstant.CONSUMER})
    @Permission(USER_CONSUMER_FEEDBACK_SHOW)
    public Response<ModelResponse<FeedbackResponse>> getConsumerFeedback(@PathVariable Long id, @PathVariable("feedback-id") Long feedbackId) {
        return new Response<>(new ModelResponse<>(feedbackService.getConsumerFeedback(userResolverService.resolveUserId(id), feedbackId)));
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
