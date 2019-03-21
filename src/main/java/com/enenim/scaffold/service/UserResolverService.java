package com.enenim.scaffold.service;

import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.exception.ScaffoldException;
import com.enenim.scaffold.exception.UnAuthorizedException;
import com.enenim.scaffold.model.cache.LoginCache;
import com.enenim.scaffold.model.dao.Vendor;
import com.enenim.scaffold.model.dao.Consumer;
import com.enenim.scaffold.model.dao.Staff;
import com.enenim.scaffold.service.dao.VendorService;
import com.enenim.scaffold.service.dao.ConsumerService;
import com.enenim.scaffold.service.dao.StaffService;
import com.enenim.scaffold.util.JsonConverter;
import com.enenim.scaffold.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserResolverService {
    private final StaffService staffService;
    private final VendorService vendorService;
    private final ConsumerService consumerService;

    @Autowired
    public UserResolverService(StaffService staffService, VendorService vendorService, ConsumerService consumerService) {
        this.staffService = staffService;
        this.vendorService = vendorService;
        this.consumerService = consumerService;
    }

    public Object getUser(String userType, Long userId){
        if(RoleConstant.STAFF.equalsIgnoreCase(userType))return staffService.getStaff(userId);
        if(RoleConstant.VENDOR.equalsIgnoreCase(userType))return vendorService.getVendor(userId);
        if(RoleConstant.CONSUMER.equalsIgnoreCase(userType))return consumerService.getConsumer(userId);
        throw new ScaffoldException("invalid_role", userType);
    }

    public Long resolveUserId(Long userId){
        if(RoleConstant.STAFF.equalsIgnoreCase(RequestUtil.getLoginToken().getUserType())) {
            return userId;
        } else{
            return RequestUtil.getLoginToken().getUserId();
        }
    }

    public String isValidRole(String[] roles){
        LoginCache login = RequestUtil.getLoginToken();
        for(String role : roles){
            if(role.equalsIgnoreCase(login.getUserType())){
                return role;
            }
        }
        throw new ScaffoldException("invalid_role", login.getUserType(), HttpStatus.FORBIDDEN);
    }

    public void setUserByRole(String role){
        Object user = RequestUtil.getLoginToken().getUser();
        if(RoleConstant.STAFF.equalsIgnoreCase(role)) {
            Staff staff = JsonConverter.getObject(user, Staff.class);
            RequestUtil.setStaff(staff);
        }
        else if(RoleConstant.VENDOR.equalsIgnoreCase(role)){
            RequestUtil.setVendor(JsonConverter.getObject(user, Vendor.class));
        }
        else if(RoleConstant.CONSUMER.equalsIgnoreCase(role)){
            RequestUtil.setConsumer(JsonConverter.getObject(user, Consumer.class));
        }
        else {
            throw new UnAuthorizedException("invalid_role", role);
        }
    }
}