package com.enenim.scaffold.service;

import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.exception.ScaffoldException;
import com.enenim.scaffold.exception.UnAuthorizedException;
import com.enenim.scaffold.model.cache.LoginCache;
import com.enenim.scaffold.model.dao.Biller;
import com.enenim.scaffold.model.dao.Consumer;
import com.enenim.scaffold.model.dao.Staff;
import com.enenim.scaffold.service.dao.BillerService;
import com.enenim.scaffold.service.dao.ConsumerService;
import com.enenim.scaffold.service.dao.StaffService;
import com.enenim.scaffold.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserResolverService {
    private final StaffService staffService;
    private final BillerService billerService;
    private final ConsumerService consumerService;

    @Autowired
    public UserResolverService(StaffService staffService, BillerService billerService, ConsumerService consumerService) {
        this.staffService = staffService;
        this.billerService = billerService;
        this.consumerService = consumerService;
    }

    public Object getUser(String userType, Long userId){
        if(RoleConstant.STAFF.equalsIgnoreCase(userType))return staffService.getStaff(userId);
        if(RoleConstant.BILLER.equalsIgnoreCase(userType))return billerService.getBiller(userId);
        if(RoleConstant.CONSUMER.equalsIgnoreCase(userType))return consumerService.getConsumer(userId);
        throw new ScaffoldException("invalid_role", userType);
    }

    public String isValidRole(String[] roles){
        LoginCache login = RequestUtil.getLoginToken();
        for(String role : roles){
            if(role.equalsIgnoreCase(login.getUserType())){
                return role;
            }
        }
        return null;
    }

    public void setUserByRole(String role){
        Object user = RequestUtil.getLoginToken().getUser();
        if(RoleConstant.STAFF.equalsIgnoreCase(role)) RequestUtil.setStaff((Staff) user);
        else if(RoleConstant.BILLER.equalsIgnoreCase(role))RequestUtil.setBiller((Biller) user);
        else if(RoleConstant.CONSUMER.equalsIgnoreCase(role))RequestUtil.setConsumer((Consumer) user);
        else {
            throw new UnAuthorizedException("invalid_role", role);
        }
    }
}