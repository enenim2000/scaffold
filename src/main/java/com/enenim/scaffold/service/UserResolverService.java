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
import org.springframework.stereotype.Service;

@Service
public class UserResolverService {
    private final StaffService staffService;
    private final BillerService billerService;
    private final ConsumerService consumerService;

    public UserResolverService(StaffService staffService, BillerService billerService, ConsumerService consumerService) {
        this.staffService = staffService;
        this.billerService = billerService;
        this.consumerService = consumerService;
    }

    public Object getUser(String userType, Long userId){
        if(RoleConstant.STAFF.equalsIgnoreCase(userType))return staffService.getStaff(userId);
        if(RoleConstant.BILLER.equalsIgnoreCase(userType))return billerService.getBiller(userId);
        if(RoleConstant.CONSUMER.equalsIgnoreCase(userType))return consumerService.getConsumer(userId);
        throw new ScaffoldException("invalid_actor", userType);
    }

    public Object getUsers(String userType){
        if(RoleConstant.STAFF.equalsIgnoreCase(userType))return staffService.getStaff();
        if(RoleConstant.BILLER.equalsIgnoreCase(userType))return billerService.getBillers();
        if(RoleConstant.CONSUMER.equalsIgnoreCase(userType))return consumerService.getConsumers();
        throw new ScaffoldException("invalid_actor", userType);
    }

    private void setUsers(String roles){
        if(roles.contains(",")){
            String[] actors = roles.split(",");
            for(String actor : actors){
                setUser(actor);
            }
        }else {
            setUser(roles);
        }
    }

    private void setUser(String role){
        LoginCache login = RequestUtil.getLoginToken();
        Object user = login.getUser();
        if(user instanceof Staff) RequestUtil.getRequest().setAttribute(RoleConstant.STAFF, user);
        if(user instanceof Biller)RequestUtil.getRequest().setAttribute(RoleConstant.BILLER, user);
        if(user instanceof Consumer)RequestUtil.getRequest().setAttribute(RoleConstant.CONSUMER, user);
        throw new UnAuthorizedException("invalid_actor", role);
    }

    public boolean isValidRole(String[] roles){
        LoginCache login = RequestUtil.getLoginToken();
        for(String role : roles){
            if(role.equalsIgnoreCase(login.getUserType())){
                setUsers(login.getUserType());
                return true;
            }
        }
        return false;
    }
}