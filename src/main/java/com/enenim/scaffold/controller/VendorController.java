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
import com.enenim.scaffold.model.dao.Vendor;
import com.enenim.scaffold.model.dao.Login;
import com.enenim.scaffold.service.FileStorageService;
import com.enenim.scaffold.service.MailSenderService;
import com.enenim.scaffold.service.cache.SharedExpireCacheService;
import com.enenim.scaffold.service.dao.VendorService;
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
@RequestMapping("/vendors")
public class VendorController {

    private final VendorService vendorService;
    private final LoginService loginService;
    private final MailSenderService mailSenderService;
    private final SharedExpireCacheService sharedExpireCacheService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final FileStorageService fileStorageService;

    @Autowired
    public VendorController(VendorService vendorService, LoginService loginService, MailSenderService mailSenderService, SharedExpireCacheService sharedExpireCacheService, BCryptPasswordEncoder bCryptPasswordEncoder, FileStorageService fileStorageService) {
        this.vendorService = vendorService;
        this.loginService = loginService;
        this.mailSenderService = mailSenderService;
        this.sharedExpireCacheService = sharedExpireCacheService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.fileStorageService = fileStorageService;
    }

    @Get
    @Permission(RouteConstant.USER_VENDOR_INDEX)
    public Response<PageResponse<Vendor>> getVendors() {
        return new Response<>(new PageResponse<>(vendorService.getVendors()));
    }

    @Get("/{id}")
    @Permission(RouteConstant.USER_VENDOR_SHOW)
    public Response<ModelResponse<Vendor>> showVendor(@PathVariable Long id) {
        return new Response<>(new ModelResponse<>(vendorService.getVendor(id)));
    }

    @Post
    @Role({RoleConstant.STAFF})
    @Permission(RouteConstant.USER_VENDOR_CREATE)
    public Response<ModelResponse<Vendor>> createVendor(@RequestParam("vendor") String vendorRequest, @RequestParam(value = "file", required = false) MultipartFile file){
        VendorRequest2 request = JsonConverter.getObject(vendorRequest, VendorRequest2.class);
        Vendor vendor = request.buildModel();
        vendor.setCommonProperties(bCryptPasswordEncoder);
        vendor.setVerified(VerifyStatus.VERIFIED);
        storeVendorLogo(vendor, file);
        return new Response<>(new ModelResponse<>(vendorService.saveVendor(vendor)));
    }

    @Post("/sign-up")
    public Response<ModelResponse<Vendor>> signUpVendor(@Valid @RequestBody Request<VendorSignUpRequest> request){
        if(!request.getBody().getPassword().equals(request.getBody().getConfirmPassword())){
            throw new ScaffoldException("password_mismatch");
        }

        Vendor vendor = request.getBody().buildModel();
        vendor.setCommonProperties(bCryptPasswordEncoder);
        vendor.skipAuthorization(true);
        vendor = vendorService.saveVendor(vendor);

        if(!StringUtils.isEmpty(vendor)){
            Login login = new Login();
            login.setUsername(vendor.getEmail());
            login.setUserType(RoleConstant.VENDOR);
            login.setUserId(vendor.getId());
            login.setPassword(bCryptPasswordEncoder.encode(request.getBody().getPassword()));
            login = loginService.saveLogin(login);
            if(!StringUtils.isEmpty(login.getId())){
                mailSenderService.send(vendor);
            }
            RequestUtil.setMessage(CommonMessage.msg("vendor_signup_success"));
            return new Response<>(new ModelResponse<>(vendor));
        }
        throw new ScaffoldException("signup_failed");
    }

    @Put("/{code}/verify")
    public Response<ModelResponse<Vendor>> verifyCode(@PathVariable("code") String code) throws Exception {
        String cacheCode = SharedExpireCacheService.SINGUP + SharedExpireCacheService.SEPARATOR + code;
        Object value = sharedExpireCacheService.get(cacheCode);
        if(!StringUtils.isEmpty(value)){
            Vendor vendor = JsonConverter.getObject(value, Vendor.class);
            vendor.setVerified(VerifyStatus.VERIFIED);
            vendor.skipAuthorization(true);
            vendor = vendorService.saveVendor(vendor);
            loginService.updateVerifyStatus(VerifyStatus.VERIFIED, vendor.getEmail());
            sharedExpireCacheService.delete(cacheCode);
            RequestUtil.setMessage(CommonMessage.msg("vendor_code_verified"));
            return new Response<>(new ModelResponse<>(vendor));
        }
        throw new ScaffoldException("invalid_expired_code");
    }

    @Post("/{email}/change-password")
    public Response<BooleanResponse> changePassword(@PathVariable("email") String email, @Valid @RequestBody Request<VendorChangePasswordRequest> request){

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

    //Header => Content-Type = multipart/form-data
    @PutMapping(value = "/{id}/details")
    @Role({RoleConstant.VENDOR})
    public Response<ModelResponse<VendorResponse>> updateVendorDetails(@PathVariable("id") Long id, @RequestPart("vendor") String vendor, @RequestPart(value = "file", required = false) MultipartFile file){

        Vendor vendorModel = vendorService.getVendor(id);

        if(vendorModel.getVerified() == VerifyStatus.NOT_VERIFIED){
            throw new ScaffoldException("code_not_verified");
        }

        VendorRequest request = JsonConverter.getObject(vendor, VendorRequest.class);

        vendorService.validateDependencies(request);

        vendorModel.setAddress(request.getAddress());
        vendorModel.setName(request.getName());
        vendorModel.setPhoneNumber(request.getPhoneNumber());
        vendorModel.setTradingName(request.getTradingName());
        vendorModel.skipAuthorization(true);

        storeVendorLogo(vendorModel, file);

        vendorModel = vendorService.saveVendor(vendorModel);

        return new Response<>(new ModelResponse<>(ObjectMapperUtil.map(vendorModel, VendorResponse.class)));
    }

    @Post("/{email}/code/re-send")
    public Response<BooleanResponse> reSendCode(@PathVariable("email") String email){
        Vendor vendor = vendorService.getVendorByEmail(email);
        if(!StringUtils.isEmpty(vendor)){
            if (vendor.getVerified() == VerifyStatus.NOT_VERIFIED){
                mailSenderService.send(vendor);
                return new Response<>(new BooleanResponse(true));
            }else if(vendor.getVerified() == VerifyStatus.VERIFIED){
                throw new ScaffoldException("verification_vendor_status");
            }
        }
        return new Response<>(new BooleanResponse(false));
    }

    @Put("/{id}/go-alive")
    @Role({RoleConstant.STAFF, RoleConstant.VENDOR})
    @Permission(RouteConstant.USER_VENDOR_GOLIVE)
    public Response<BooleanResponse> goLiveToggle(@PathVariable Long id){
        Vendor vendor = vendorService.getVendor(id);
        if(StringUtils.isEmpty(vendor.getAddress())){
            throw new ScaffoldException("biiler_address_required");
        }
        if(StringUtils.isEmpty(vendor.getLogoPath())){
            throw new ScaffoldException("vendor_logo_required");
        }
        vendor.setEnabled(EnabledStatus.ENABLED);
        vendor = vendorService.saveVendor(vendor);
        boolean allow = false;
        if(vendor.getEnabled() == EnabledStatus.ENABLED){
            allow = true;
        }
        return new Response<>(new BooleanResponse(allow));
    }

    @Put("/{id}/toggle")
    @Role({RoleConstant.STAFF})
    @Permission(RouteConstant.USER_VENDOR_TOGGLE)
    public Response<BooleanResponse> toggle(@PathVariable Long id){
        return new Response<>(new BooleanResponse(vendorService.toggle(id)));
    }

    private void storeVendorLogo(Vendor vendor, MultipartFile file){
        if(!StringUtils.isEmpty(file)){
            long file_size = Long.valueOf(SpringMessage.msg("vendor_logo_upload_size"));
            if(file.getSize() > file_size){
                String file_size_kb = (file_size/1000) + "";
                throw new ScaffoldException("file_size_limit", file_size_kb,  HttpStatus.PAYLOAD_TOO_LARGE);
            }
            String fileName = fileStorageService.storeFile(file, "vendor-logo-" + vendor.getSlug() + ".jpg");
            vendor.setLogoPath("/assets" + AssetBaseConstant.VENDOR + fileName);
        }
    }
}
