package com.enenim.scaffold.service;

import com.enenim.scaffold.exception.UnAuthorizedException;
import com.enenim.scaffold.model.dao.Beneficiary;
import com.enenim.scaffold.model.dao.Biller;
import com.enenim.scaffold.model.dao.Staff;
import com.enenim.scaffold.service.dao.ConsumerService;
import com.enenim.scaffold.service.dao.StaffService;
import com.enenim.scaffold.util.CommonUtil;
import com.enenim.scaffold.util.RequestUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserResolverService {
    private final StaffService staffService;
    private final BillerService billerService;
    private final ConsumerService consumerService;
    private final BillerUserService billerUserService;
    private final BeneficiaryService beneficiaryService;

    public UserResolverService(StaffService staffService, BillerService billerService, ConsumerService consumerService, BillerUserService billerUserService, BeneficiaryService beneficiaryService) {
        this.staffService = staffService;
        this.billerService = billerService;
        this.consumerService = consumerService;
        this.billerUserService = billerUserService;
        this.beneficiaryService = beneficiaryService;
    }

    public String formatUserType(String userType){
        userType = userType.split("App\\\\")[1];
        userType = userType.replace("\\", "");
        return userType;
    }

    public Object getUser(String userType, Long userId){
        userType = formatUserType(userType);
        if(ActorConstant.STAFF_KEY.equalsIgnoreCase(userType))return staffService.getStaff(userId);
        if(ActorConstant.BILLER_KEY.equalsIgnoreCase(userType))return billerService.getBiller(userId);
        if(ActorConstant.BENEFICIARY_KEY.equalsIgnoreCase(userType))return beneficiaryService.getBeneficiary(userId);
        if(ActorConstant.CONSUMER_KEY.equalsIgnoreCase(userType))return consumerService.getConsumer(userId);
        if(ActorConstant.BILLER_USER_KEY.equalsIgnoreCase(userType))return billerUserService.getBillerUser(userId);
        throw new BAPException(HttpStatus.UNAUTHORIZED.toString(), null, CommonUtil.msg("invalid_actor", userType));
    }

    public Object getUsers(String userType){
        userType = formatUserType(userType);
        if(ActorConstant.STAFF_KEY.equalsIgnoreCase(userType))return staffService.getStaff();
        if(ActorConstant.BILLER_KEY.equalsIgnoreCase(userType))return billerService.getBillers();
        if(ActorConstant.BENEFICIARY_KEY.equalsIgnoreCase(userType))return beneficiaryService.getBeneficiaries();
        if(ActorConstant.CONSUMER_KEY.equalsIgnoreCase(userType))return consumerService.getConsumers();
        if(ActorConstant.BILLER_USER_KEY.equalsIgnoreCase(userType))return billerUserService.getBillerUsers();
        throw new BAPException(HttpStatus.UNAUTHORIZED.toString(), null, CommonUtil.msg("invalid_actor", userType));
    }

    public void setUsers(String roles){
        String[] actors = roles.split(",");
        for(String actor : actors){
            setUser(actor);
        }
    }

    private void setUser(String role){
        LoginToken login = RequestUtil.getLoginToken();
        Object user = login.getUser();
        if(user instanceof Staff) RequestUtil.getRequest().setAttribute(ActorConstant.STAFF_KEY, user);
        if(user instanceof Biller)RequestUtil.getRequest().setAttribute(ActorConstant.BILLER_KEY, user);
        if(user instanceof Consumer)RequestUtil.getRequest().setAttribute(ActorConstant.CONSUMER_KEY, user);
        if(user instanceof Beneficiary)RequestUtil.getRequest().setAttribute(ActorConstant.BENEFICIARY_KEY, user);
        if(user instanceof BillerUser)RequestUtil.getRequest().setAttribute(ActorConstant.BILLER_USER_KEY, user);
        throw new UnAuthorizedException(CommonUtil.msg("invalid_actor", role));
    }

    public boolean validRole(String roles){
        String actor = "";
        LoginToken login = RequestUtil.getLoginToken();
        if(!roles.contains(",")){
            String role = formatUserType(login.getUserType());
            if(actor.equalsIgnoreCase(role)){
                setUser(role);
                return true;
            }
        }else {
            String[] actors = roles.split(",");
            for(String $actor : actors){
                String role = formatUserType(login.getUserType());
                if($actor.equalsIgnoreCase(role)){
                    setUser(role);
                    return true;
                }
            }
        }
        return false;
    }
}