package com.enenim.scaffold.controller;

import com.enenim.scaffold.annotation.*;
import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.constant.RouteConstant;
import com.enenim.scaffold.dto.request.BillerRequest;
import com.enenim.scaffold.dto.request.BillerSignUpVerifyRequest;
import com.enenim.scaffold.dto.request.Request;
import com.enenim.scaffold.dto.response.BooleanResponse;
import com.enenim.scaffold.dto.response.ModelResponse;
import com.enenim.scaffold.dto.response.PageResponse;
import com.enenim.scaffold.dto.response.Response;
import com.enenim.scaffold.enums.VerifyStatus;
import com.enenim.scaffold.exception.ScaffoldException;
import com.enenim.scaffold.model.dao.Biller;
import com.enenim.scaffold.model.dao.Login;
import com.enenim.scaffold.service.FileStorageService;
import com.enenim.scaffold.service.MailSenderService;
import com.enenim.scaffold.service.cache.SharedExpireCacheService;
import com.enenim.scaffold.service.dao.BillerService;
import com.enenim.scaffold.service.dao.LoginService;
import com.enenim.scaffold.util.JsonConverter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private final FileStorageService fileStorageService;

    @Autowired
    public BillerController(BillerService billerService, LoginService loginService, MailSenderService mailSenderService, SharedExpireCacheService sharedExpireCacheService, BCryptPasswordEncoder bCryptPasswordEncoder, FileStorageService fileStorageService) {
        this.billerService = billerService;
        this.loginService = loginService;
        this.mailSenderService = mailSenderService;
        this.sharedExpireCacheService = sharedExpireCacheService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.fileStorageService = fileStorageService;
    }

    @Get
    @Permission(RouteConstant.USER_BILLER_INDEX)
    public Response<PageResponse<Biller>> getBillers() {
        return new Response<>(new PageResponse<>(billerService.getBillers()));
    }

    @Get("/{id}")
    @Permission(RouteConstant.USER_BILLER_SHOW)
    public Response<ModelResponse<Biller>> showConsumer(@PathVariable Long id) {
        return new Response<>(new ModelResponse<>(billerService.getBiller(id)));
    }

    @Post
    @Role({RoleConstant.STAFF, RoleConstant.BILLER})
    @Permission(RouteConstant.USER_BILLER_CREATE)
    public Response<ModelResponse<Biller>> createBiller(@RequestParam("biller") String billerRequest, @RequestParam(value = "file", required = false) MultipartFile file){
        BillerRequest request = JsonConverter.getObject(billerRequest, BillerRequest.class);
        Biller biller = request.buildModel();
        biller.setTestSecret(bCryptPasswordEncoder.encode(new Date().toString() + Math.random()));
        biller.setSecret(bCryptPasswordEncoder.encode(new Date().toString() + Math.random()));
        biller.setVerified(VerifyStatus.VERIFIED);

        String slug = RandomStringUtils.randomAlphanumeric(30);
        biller.setSlug(slug);

        if(!StringUtils.isEmpty(file)){

            long file_size = 10000;
            if(file.getSize() > file_size){
                String file_size_kb = (file_size/1000) + "";
                throw new ScaffoldException("file_size_biller", file_size_kb,  HttpStatus.PAYLOAD_TOO_LARGE);
            }

            String fileName = fileStorageService.storeFile(file, "image-" + slug + ".jpg");
            biller.setLogoPath("/assets/logos/" + fileName);
        }

        return new Response<>(new ModelResponse<>(billerService.saveBiller(biller)));
    }

    @Post("/sign-up")
    public Response<ModelResponse<Biller>> signUpConsumers(@Valid @RequestBody Request<BillerRequest> request){
        billerService.validateDependencies(request.getBody());
        Biller biller = request.getBody().buildModel();
        biller.skipAuthorization(true);
        biller = billerService.saveBiller(biller);

        if(!StringUtils.isEmpty(biller)){
            Login login = new Login();
            login.setUsername(biller.getEmail());
            login.setUserType(RoleConstant.BILLER);
            login.setUserId(biller.getId());
            login = loginService.saveLogin(login);
            if(!StringUtils.isEmpty(login.getId())){
                mailSenderService.send(biller);
            }
            return new Response<>(new ModelResponse<>(biller));
        }
        throw new ScaffoldException("biller_signup_failed");
    }

    @Put("/{code}/verify")
    public Response<ModelResponse<Biller>> verifyCode(@PathVariable("code") String code, @Valid @RequestBody BillerSignUpVerifyRequest request) throws Exception {
        String cacheCode = SharedExpireCacheService.SINGUP + SharedExpireCacheService.SEPARATOR + code;
        Object value = sharedExpireCacheService.get(cacheCode);
        if(!StringUtils.isEmpty(value)){
            Biller biller = JsonConverter.getObject(value, Biller.class);
            Login login = loginService.getLoginByUsername(biller.getEmail());
            login.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
            login = loginService.saveLogin(login);
            if(!StringUtils.isEmpty(login)){
                biller.setVerified(VerifyStatus.VERIFIED);
                biller.skipAuthorization(true);
                biller = billerService.saveBiller(biller);
                sharedExpireCacheService.delete(cacheCode);
                return new Response<>(new ModelResponse<>(biller));
            }
        }
        throw new ScaffoldException("invalid_expired_code");
    }

    @Post("/{email}/code/re-send")
    public Response<BooleanResponse> reSendCode(@PathVariable("email") String email){
        Biller biller = billerService.getBillerByEmail(email);
        if(!StringUtils.isEmpty(biller)){
            if (biller.getVerified() == VerifyStatus.NOT_VERIFIED){
                mailSenderService.send(biller);
                return new Response<>(new BooleanResponse(true));
            }else if(biller.getVerified() == VerifyStatus.VERIFIED){
                throw new ScaffoldException("verification_biller_status");
            }
        }
        return new Response<>(new BooleanResponse(false));
    }

    @Put("/{id}/toggle")
    @Role({RoleConstant.STAFF})
    @Permission(RouteConstant.USER_BILLER_TOGGLE)
    public Response<BooleanResponse> toggle(@PathVariable Long id){
        return new Response<>(new BooleanResponse(billerService.toggle(id)));
    }
}
