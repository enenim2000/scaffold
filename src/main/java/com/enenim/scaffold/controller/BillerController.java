package com.enenim.scaffold.controller;

import com.enenim.scaffold.annotation.*;
import com.enenim.scaffold.constant.AssetBaseConstant;
import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.constant.RouteConstant;
import com.enenim.scaffold.dto.request.*;
import com.enenim.scaffold.dto.response.*;
import com.enenim.scaffold.enums.EnabledStatus;
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
import com.enenim.scaffold.util.ObjectMapperUtil;
import com.enenim.scaffold.util.RequestUtil;
import com.enenim.scaffold.util.message.CommonMessage;
import com.enenim.scaffold.util.message.SpringMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

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
    public Response<ModelResponse<Biller>> showBiller(@PathVariable Long id) {
        return new Response<>(new ModelResponse<>(billerService.getBiller(id)));
    }

    @Post
    @Role({RoleConstant.STAFF})
    @Permission(RouteConstant.USER_BILLER_CREATE)
    public Response<ModelResponse<Biller>> createBiller(@RequestParam("biller") String billerRequest, @RequestParam(value = "file", required = false) MultipartFile file){
        BillerRequest2 request = JsonConverter.getObject(billerRequest, BillerRequest2.class);
        Biller biller = request.buildModel();
        biller.setCommonProperties(bCryptPasswordEncoder);
        biller.setVerified(VerifyStatus.VERIFIED);
        storeBillerLogo(biller, file);
        return new Response<>(new ModelResponse<>(billerService.saveBiller(biller)));
    }

    @Post("/sign-up")
    public Response<ModelResponse<Biller>> signUpBiller(@Valid @RequestBody Request<BillerSignUpRequest> request){
        if(!request.getBody().getPassword().equals(request.getBody().getConfirmPassword())){
            throw new ScaffoldException("password_mismatch");
        }

        Biller biller = request.getBody().buildModel();
        biller.skipAuthorization(true);
        biller = billerService.saveBiller(biller);

        if(!StringUtils.isEmpty(biller)){
            Login login = new Login();
            login.setUsername(biller.getEmail());
            login.setUserType(RoleConstant.BILLER);
            login.setUserId(biller.getId());
            login.setPassword(bCryptPasswordEncoder.encode(request.getBody().getPassword()));
            login = loginService.saveLogin(login);
            if(!StringUtils.isEmpty(login.getId())){
                mailSenderService.send(biller);
            }
            RequestUtil.setMessage(CommonMessage.msg("biller_signup_success"));
            return new Response<>(new ModelResponse<>(biller));
        }
        throw new ScaffoldException("signup_failed");
    }

    @Put("/{code}/verify")
    public Response<ModelResponse<Biller>> verifyCode(@PathVariable("code") String code) throws Exception {
        String cacheCode = SharedExpireCacheService.SINGUP + SharedExpireCacheService.SEPARATOR + code;
        Object value = sharedExpireCacheService.get(cacheCode);
        if(!StringUtils.isEmpty(value)){
            Biller biller = JsonConverter.getObject(value, Biller.class);
            biller.setVerified(VerifyStatus.VERIFIED);
            biller.skipAuthorization(true);
            biller = billerService.saveBiller(biller);
            loginService.updateVerifyStatus(VerifyStatus.VERIFIED, biller.getEmail());
            sharedExpireCacheService.delete(cacheCode);
            RequestUtil.setMessage(CommonMessage.msg("biller_code_verified"));
            return new Response<>(new ModelResponse<>(biller));
        }
        throw new ScaffoldException("invalid_expired_code");
    }

    @Post("/{email}/change-password")
    public Response<BooleanResponse> changePassword(@PathVariable("email") String email, @Valid @RequestBody Request<BillerChangePasswordRequest> request){

        if(!request.getBody().getPassword().equals(request.getBody().getConfirmPassword())){
            throw new ScaffoldException("password_mismatch");
        }

        Login login = loginService.getLoginByUsername(email);

        if(!bCryptPasswordEncoder.matches(request.getBody().getOldPassword(), login.getPassword())){
            throw new ScaffoldException("old_password_mismatch");
        }

        if(!StringUtils.isEmpty(login)){
            login.setPassword(bCryptPasswordEncoder.encode(request.getBody().getPassword()));
            loginService.saveLogin(login);
            return new Response<>(new BooleanResponse(true));
        }

        return new Response<>(new BooleanResponse(false));
    }

    @PutMapping(value = "/{id}/details")
    //@Role({RoleConstant.BILLER})
    public Response<ModelResponse<BillerResponse>> updateBillerDetails(@PathVariable("id") Long id, @RequestParam(value = "biller", required = false) String biller, @RequestParam(value = "file", required = false) MultipartFile file){

        System.out.println("biller = " + biller);

        Biller billerModel = billerService.getBiller(id);

        if(billerModel.getVerified() == VerifyStatus.NOT_VERIFIED){
            throw new ScaffoldException("code_not_verified");
        }

        System.out.println("billerRequest = " + biller);
        System.out.println("file name= " + file.getOriginalFilename());

        BillerRequest request = JsonConverter.getObject(biller, BillerRequest.class);

        billerService.validateDependencies(request);

        billerModel.setAddress(request.getAddress());
        billerModel.setName(request.getName());
        billerModel.setPhoneNumber(request.getPhoneNumber());
        billerModel.setTradingName(request.getTradingName());
        billerModel.setCommonProperties(bCryptPasswordEncoder);
        billerModel.skipAuthorization(true);

        storeBillerLogo(billerModel, file);

        System.out.println("biller = " + JsonConverter.getJsonRecursive(billerModel));

        billerModel = billerService.saveBiller(billerModel);

        if(!StringUtils.isEmpty(billerModel)){
            Login login = new Login();
            login.setUsername(billerModel.getEmail());
            login.setUserType(RoleConstant.BILLER);
            login.setUserId(billerModel.getId());
            login = loginService.saveLogin(login);
            if(!StringUtils.isEmpty(login.getId())){
                mailSenderService.send(billerModel);
            }
            return new Response<>(new ModelResponse<>(ObjectMapperUtil.map(billerModel, BillerResponse.class)));
        }
        throw new ScaffoldException("biller_signup_failed");
    }

    /*@Put("/{code}/verify")
    public Response<ModelResponse<Biller>> verifyCode(@PathVariable("code") String code, @Valid @RequestBody BillerNewPasswordRequest request) throws Exception {
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
    }*/

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

    @Put("/{id}/go-alive")
    @Role({RoleConstant.STAFF, RoleConstant.BILLER})
    @Permission(RouteConstant.USER_BILLER_GOLIVE)
    public Response<BooleanResponse> goLiveToggle(@PathVariable Long id){
        Biller biller = billerService.getBiller(id);
        if(StringUtils.isEmpty(biller.getAddress())){
            throw new ScaffoldException("biiler_address_required");
        }
        if(StringUtils.isEmpty(biller.getLogoPath())){
            throw new ScaffoldException("biller_logo_required");
        }
        biller.setEnabled(EnabledStatus.ENABLED);
        biller = billerService.saveBiller(biller);
        boolean allow = false;
        if(biller.getEnabled() == EnabledStatus.ENABLED){
            allow = true;
        }
        return new Response<>(new BooleanResponse(allow));
    }

    @Put("/{id}/toggle")
    @Role({RoleConstant.STAFF})
    @Permission(RouteConstant.USER_BILLER_TOGGLE)
    public Response<BooleanResponse> toggle(@PathVariable Long id){
        return new Response<>(new BooleanResponse(billerService.toggle(id)));
    }

    private void storeBillerLogo(Biller biller, MultipartFile file){
        if(!StringUtils.isEmpty(file)){
            long file_size = Long.valueOf(SpringMessage.msg("biller_logo_upload_size"));
            if(file.getSize() > file_size){
                String file_size_kb = (file_size/1000) + "";
                throw new ScaffoldException("file_size_biller", file_size_kb,  HttpStatus.PAYLOAD_TOO_LARGE);
            }
            String fileName = fileStorageService.storeFile(file, "biller-logo-" + biller.getSlug() + ".jpg");
            biller.setLogoPath("/assets" + AssetBaseConstant.BILLER + fileName);
        }
    }
}
