package com.enenim.scaffold.service;

import com.enenim.scaffold.enums.AuthorizationStatus;
import com.enenim.scaffold.model.dao.Authorization;
import com.enenim.scaffold.repository.dao.AuthorizationRepository;
import com.enenim.scaffold.util.PageRequestUtil;
import com.enenim.scaffold.util.RequestUtil;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorizationService {
    private final AuthorizationRepository authorizationRepository;

    @Autowired
    public AuthorizationService(AuthorizationRepository authorizationRepository) {
        this.authorizationRepository = authorizationRepository;
    }

    public Page<Authorization> getAuthorizations(){
        return authorizationRepository.findAll(PageRequestUtil.getPageRequest());
    }

    @NotFound(action = NotFoundAction.IGNORE)
    public Authorization getAuthorization(Long id){
        return authorizationRepository.findOrFail(id);
    }

    public Authorization saveAuthorization(Authorization authorization){
        return authorizationRepository.save(authorization);
    }

    public List<Authorization> saveAuthorizations(List<Authorization> authorizations){
        return authorizationRepository.saveAll(authorizations);
    }

    public void deleteAuthorization(Long id){
        authorizationRepository.deleteById(id);
    }

    public Page<Authorization> findAuthorizationByMe(){
        return authorizationRepository.findAuthorizationByMe(RequestUtil.getLoginToken().getUserId(), PageRequestUtil.getPageRequest());
    }

    public Page<Authorization> findAuthorizationByMeAndType(String type){
        return authorizationRepository.findAuthorizationByMeAndType(RequestUtil.getLoginToken().getUserId(), AuthorizationStatus.valueOf(type), PageRequestUtil.getPageRequest());
    }

    public Page<Authorization> findAuthorizationByAll(){
        return authorizationRepository.findAuthorizationByAll(PageRequestUtil.getPageRequest());
    }

    public Page<Authorization> findAuthorizationByAllAndType(String type){
        return authorizationRepository.findAuthorizationByAllAndType(AuthorizationStatus.valueOf(type), PageRequestUtil.getPageRequest());
    }

    public Page<Authorization> findAuthorizationByMeAndTypes(){
        return authorizationRepository.findAuthorizationByMeAndTypes(RequestUtil.getLoginToken().getUserId(), PageRequestUtil.getPageRequest());
    }

    public Page<Authorization> findAuthorizationByChecked(){
        return authorizationRepository.findAuthorizationByChecked(PageRequestUtil.getPageRequest());
    }

    public Page<Authorization> findAuthorizationByCheckedAndType(String type){
        return authorizationRepository.findAuthorizationByCheckedAndType(AuthorizationStatus.valueOf(type), PageRequestUtil.getPageRequest());
    }

    public void process(){

    }
}