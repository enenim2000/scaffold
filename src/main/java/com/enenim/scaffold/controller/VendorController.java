package com.enenim.scaffold.controller;

import com.enenim.scaffold.annotation.*;
import com.enenim.scaffold.constant.AssetBaseConstant;
import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.constant.RouteConstant;
import com.enenim.scaffold.dto.request.*;
import com.enenim.scaffold.dto.request.part.TransactionFilterRequest;
import com.enenim.scaffold.dto.response.BooleanResponse;
import com.enenim.scaffold.dto.response.ModelResponse;
import com.enenim.scaffold.dto.response.PageResponse;
import com.enenim.scaffold.dto.response.Response;
import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.enums.VerifyStatus;
import com.enenim.scaffold.exception.ScaffoldException;
import com.enenim.scaffold.model.dao.*;
import com.enenim.scaffold.service.FileStorageService;
import com.enenim.scaffold.service.MailSenderService;
import com.enenim.scaffold.service.UserResolverService;
import com.enenim.scaffold.service.cache.SharedExpireCacheService;
import com.enenim.scaffold.service.dao.LoginService;
import com.enenim.scaffold.service.dao.TransactionService;
import com.enenim.scaffold.service.dao.VendorService;
import com.enenim.scaffold.service.dao.VendorUserService;
import com.enenim.scaffold.util.JsonConverter;
import com.enenim.scaffold.util.ObjectMapperUtil;
import com.enenim.scaffold.util.PasswordEncoder;
import com.enenim.scaffold.util.RequestUtil;
import com.enenim.scaffold.util.message.CommonMessage;
import com.enenim.scaffold.util.message.SpringMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static com.enenim.scaffold.constant.RouteConstant.USER_VENDOR_SERVICE_INDEX;
import static com.enenim.scaffold.constant.RouteConstant.USER_VENDOR_TRANSACTION_INDEX;

@RestController
@RequestMapping("/vendors")
public class VendorController {

    private final VendorUserService vendorUserService;
    private final LoginService loginService;
    private final MailSenderService mailSenderService;
    private final SharedExpireCacheService sharedExpireCacheService;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;
    private final UserResolverService userResolverService;
    private final TransactionService transactionService;
    private final VendorService vendorService;

    @Autowired
    public VendorController(VendorUserService vendorUserService, LoginService loginService, MailSenderService mailSenderService, SharedExpireCacheService sharedExpireCacheService, PasswordEncoder passwordEncoder, FileStorageService fileStorageService, UserResolverService userResolverService, TransactionService transactionService, VendorService vendorService) {
        this.vendorUserService = vendorUserService;
        this.loginService = loginService;
        this.mailSenderService = mailSenderService;
        this.sharedExpireCacheService = sharedExpireCacheService;
        this.passwordEncoder = passwordEncoder;
        this.fileStorageService = fileStorageService;
        this.userResolverService = userResolverService;
        this.transactionService = transactionService;
        this.vendorService = vendorService;
    }

    @Get
    @Role({RoleConstant.STAFF, RoleConstant.VENDOR})
    @Permission(RouteConstant.USER_VENDOR_INDEX)
    public Response<PageResponse<Vendor>> getVendors() {
        return new Response<>(new PageResponse<>(vendorUserService.getVendors()));
    }

    @Get("/{id}")
    @Role({RoleConstant.STAFF, RoleConstant.VENDOR})
    @Permission(RouteConstant.USER_VENDOR_SHOW)
    public Response<ModelResponse<Vendor>> showVendor(@PathVariable Long id) {
        return new Response<>(new ModelResponse<>(vendorUserService.getVendor(id)));
    }

    @Post
    @Role({RoleConstant.STAFF})
    @Permission(RouteConstant.USER_VENDOR_CREATE)
    public Response<ModelResponse<Vendor>> createVendor(@RequestParam("vendor") String vendorRequest, @RequestParam(value = "file", required = false) MultipartFile file){
        VendorRequest2 request = JsonConverter.getObject(vendorRequest, VendorRequest2.class);
        Vendor vendor = request.buildModel();
        vendor.setCommonProperties();
        vendor.setVerified(VerifyStatus.VERIFIED);
        storeVendorLogo(vendor, file);
        return new Response<>(new ModelResponse<>(vendorUserService.saveVendor(vendor)));
    }

    @Post("/sign-up")
    public Response<ModelResponse<Vendor>> signUpVendor(@Valid @RequestBody Request<VendorSignUpRequest> request){
        if(!request.getBody().getPassword().equals(request.getBody().getConfirmPassword())){
            throw new ScaffoldException("password_mismatch");
        }

        Vendor vendor = request.getBody().buildModel();
        vendor.setCommonProperties();
        vendor.skipAuthorization(true);
        vendor = vendorUserService.saveVendor(vendor);

        if(!StringUtils.isEmpty(vendor)){
            Login login = new Login();
            login.setUsername(vendor.getEmail());
            login.setUserType(RoleConstant.VENDOR);
            login.setUserId(vendor.getId());
            login.setPassword(passwordEncoder.encode(request.getBody().getPassword()));
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
            vendor = vendorUserService.saveVendor(vendor);
            loginService.updateVerifyStatus(VerifyStatus.VERIFIED, vendor.getEmail());
            sharedExpireCacheService.delete(cacheCode);
            RequestUtil.setMessage(CommonMessage.msg("vendor_code_verified"));
            return new Response<>(new ModelResponse<>(vendor));
        }
        throw new ScaffoldException("invalid_expired_code");
    }

    @Post("/{email}/change-password")
    @Role({RoleConstant.VENDOR})
    @Permission(RouteConstant.USER_VENDOR_CHANGE_PASSWORD)
    public Response<BooleanResponse> changePassword(@PathVariable("email") String email, @Valid @RequestBody Request<VendorChangePasswordRequest> request){

        if(!request.getBody().getPassword().equals(request.getBody().getConfirmPassword())){
            throw new ScaffoldException("password_mismatch");
        }

        Login login = loginService.getLoginByUsername(email);

        if(!passwordEncoder.matches(request.getBody().getOldPassword(), login.getPassword())){
            throw new ScaffoldException("old_password_mismatch");
        }

        if(!StringUtils.isEmpty(login)){
            login.setPassword(passwordEncoder.encode(request.getBody().getPassword()));
            loginService.saveLogin(login);
            return new Response<>(new BooleanResponse(true));
        }

        return new Response<>(new BooleanResponse(false));
    }

    //Header => Content-Type = multipart/form-data
    @PutMapping(value = "/{id}/profile")
    @Role({RoleConstant.VENDOR, RoleConstant.STAFF})
    @Permission(RouteConstant.USER_VENDOR_PROFILE)
    public Response<ModelResponse<Vendor>> updateVendorDetails(@PathVariable("id") Long id, @RequestPart("vendor") String vendor, @RequestPart(value = "file", required = false) MultipartFile file){

        Vendor vendorModel = vendorUserService.getVendor(id);

        if(vendorModel.getVerified() == VerifyStatus.NOT_VERIFIED){
            throw new ScaffoldException("code_not_verified");
        }

        VendorProfileRequest request = JsonConverter.getObject(vendor, VendorProfileRequest.class);

        vendorUserService.validateDependencies(request);

        vendorModel = ObjectMapperUtil.map(request, vendorModel);

        if(StringUtils.isEmpty(vendorModel.getContact())){
            vendorModel.setContact(ObjectMapperUtil.map(request.getContact(), Contact.class));
        }else {
            vendorModel.setContact(ObjectMapperUtil.map(request.getContact(), vendorModel.getContact()));
        }

        vendorModel.skipAuthorization(true);

        storeVendorLogo(vendorModel, file);

        vendorModel = vendorUserService.saveVendor(vendorModel);

        return new Response<>(new ModelResponse<>(vendorModel));
    }

    @Post("/{email}/code/re-send")
    public Response<BooleanResponse> reSendCode(@PathVariable("email") String email){
        Vendor vendor = vendorUserService.getVendorByEmail(email);
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
        Vendor vendor = vendorUserService.getVendor(id);
        if(StringUtils.isEmpty(vendor.getAddress())){
            throw new ScaffoldException("biiler_address_required");
        }
        if(StringUtils.isEmpty(vendor.getLogoPath())){
            throw new ScaffoldException("vendor_logo_required");
        }
        vendor.setEnabled(EnabledStatus.ENABLED);
        vendor = vendorUserService.saveVendor(vendor);
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
        return new Response<>(new BooleanResponse(vendorUserService.toggle(id)));
    }

    @Get({"/{id}/transactions"})
    @Role({RoleConstant.STAFF, RoleConstant.VENDOR})
    @Permission(USER_VENDOR_TRANSACTION_INDEX)
    public Response<PageResponse<Transaction>> getVendorTransactions(@PathVariable Long id, @Valid @RequestBody TransactionFilterRequest filter) {
        return new Response<>(new PageResponse<>(transactionService.getVendorTransactions(filter, userResolverService.resolveUserId(id))));
    }

    @Get({"/{id}/services"})
    @Role({RoleConstant.STAFF, RoleConstant.VENDOR})
    @Permission(USER_VENDOR_SERVICE_INDEX)
    public Response<PageResponse<Service>> getVendorServices(@PathVariable Long id) {
        return new Response<>(new PageResponse<>(vendorService.getVendorServices(userResolverService.resolveUserId(id))));
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
