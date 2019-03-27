package com.enenim.scaffold.todos.controller;

import com.enenim.scaffold.annotation.Get;
import com.enenim.scaffold.annotation.Permission;
import com.enenim.scaffold.annotation.Put;
import com.enenim.scaffold.dto.response.PageResponse;
import com.enenim.scaffold.dto.response.Response;
import com.enenim.scaffold.enums.AuthorizationStatus;
import com.enenim.scaffold.exception.ScaffoldException;
import com.enenim.scaffold.model.dao.Authorization;
import com.enenim.scaffold.model.dao.Staff;
import com.enenim.scaffold.service.AuthorizationService;
import com.enenim.scaffold.service.dao.GroupService;
import com.enenim.scaffold.service.dao.TaskService;
import com.enenim.scaffold.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.enenim.scaffold.constant.RouteConstant.*;

@RestController
@RequestMapping("/administration/authorize")
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    @Autowired
    private final GroupService groupService;

    @Autowired
    private final TaskService taskService;

    @Autowired
    public AuthorizationController(AuthorizationService authorizationService, GroupService groupService, TaskService taskService) {
        this.authorizationService = authorizationService;
        this.groupService = groupService;
        this.taskService = taskService;
    }

    @Get
    @Permission(ADMINISTRATION_AUTHORIZE_INDEX)
    public Response<PageResponse<Authorization>> getAuthorizations(){
        return new Response<>(new PageResponse<>(authorizationService.getAuthorizations()));
    }

    @Get("/me")
    @Permission(ADMINISTRATION_AUTHORIZE_ME)//authorized or rejected
    public Response<PageResponse<Authorization>> getMyAuthorizations(){
        return new Response<>(new PageResponse<>(authorizationService.findAuthorizationByMe()));
    }

    @Get("/me/{type}")//authorized or rejected
    @Permission(ADMINISTRATION_AUTHORIZE_ME)
    public Response<PageResponse<Authorization>> getMyAuthorizationsByType(@PathVariable String type){
        return new Response<>(new PageResponse<>(authorizationService.findAuthorizationByMeAndType(type.toUpperCase())));
    }

    @Get("/all")
    @Permission(ADMINISTRATION_AUTHORIZE_ALL)
    public Response<PageResponse<Authorization>> getAllAuthorizations(){
        return new Response<>(new PageResponse<>(authorizationService.findAuthorizationByAll()));
    }

    @Get("/all/{type}")
    @Permission(ADMINISTRATION_AUTHORIZE_ALL)
    public Response<PageResponse<Authorization>> getAllAuthorizationsByType(@PathVariable String type){
        return new Response<>(new PageResponse<>(authorizationService.findAuthorizationByAllAndType(type.toUpperCase())));
    }

    @Get("/checked")
    @Permission(ADMINISTRATION_AUTHORIZE_CHECKED)//Pending or forwarded
    public Response<PageResponse<Authorization>> getCheckedAuthorization(){
        return new Response<>(new PageResponse<>(authorizationService.findAuthorizationByChecked()));
    }

    @Get("/checked/{type}")
    @Permission(ADMINISTRATION_AUTHORIZE_CHECKED)//Pending or forwarded
    public Response<PageResponse<Authorization>> getCheckedAuthorizationsByType(@PathVariable String type){
        return new Response<>(new PageResponse<>(authorizationService.findAuthorizationByCheckedAndType(type.toUpperCase())));
    }

    @Get("/{id}")
    @Permission(ADMINISTRATION_AUTHORIZE_SHOW)
    public Response<Authorization> getAuthorization(@PathVariable Long id){
        return new Response<>(authorizationService.getAuthorization(id));
    }

    @Put("/{id}/forward")
    @Permission(ADMINISTRATION_AUTHORIZE_FORWARD)
    public Response<Authorization> forwardAuthorization(@PathVariable Long id){
        Authorization authorization = authorizationService.getAuthorization(id);
        authorization.setStatus(AuthorizationStatus.FORWARDED);
        return new Response<>(authorizationService.saveAuthorization(authorization));
    }

    @Put("/{id}/approve")
    @Permission(ADMINISTRATION_AUTHORIZE_APPROVE)
    public Response<Authorization> approveAuthorization(@PathVariable Long id){
        Authorization authorization = authorizationService.getAuthorization(id);
        authorization.setStatus(AuthorizationStatus.AUTHORIZED);
        authorization.setStaff(new Staff(RequestUtil.getLoginToken().getUserId()));
        return new Response<>(authorizationService.saveAuthorization(authorization));
    }

    @Put("/{id}/reject")
    @Permission(ADMINISTRATION_AUTHORIZE_REJECT)
    public Response<Authorization> rejectAuthorization(@PathVariable Long id, @RequestBody String comment){
        if(StringUtils.isEmpty(comment))throw new ScaffoldException("invalid_comment");
        Authorization authorization = authorizationService.getAuthorization(id);
        authorization.setStatus(AuthorizationStatus.REJECTED);
        authorization.setComment(comment);
        return new Response<>(authorizationService.saveAuthorization(authorization));
    }

    @Put("/forward")
    @Permission(ADMINISTRATION_AUTHORIZE_FORWARD)
    public Response<Collection<Authorization>> forwardManyAuthorization(List<Long> authorizationIds){
        List<Authorization> authorizations = authorizationService.saveAuthorizations(buildDependencies(authorizationIds));
        return new Response<>(authorizations);
    }

    private List<Authorization> buildDependencies(List<Long> authorizationIds){
        List<Authorization> authorizations = new ArrayList<>();
        for(Long authorizationId : authorizationIds){
            Authorization authorization = new Authorization(authorizationId);
            authorization.setStatus(AuthorizationStatus.FORWARDED);
            authorizations.add(authorization);
        }
        return authorizations;
    }
}