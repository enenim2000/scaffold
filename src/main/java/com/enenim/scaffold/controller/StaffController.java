package com.enenim.scaffold.controller;

import com.enenim.scaffold.annotation.*;
import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.dto.request.Request;
import com.enenim.scaffold.dto.request.StaffRequest;
import com.enenim.scaffold.dto.response.ModelResponse;
import com.enenim.scaffold.dto.response.PageResponse;
import com.enenim.scaffold.dto.response.Response;
import com.enenim.scaffold.model.dao.Staff;
import com.enenim.scaffold.service.dao.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.enenim.scaffold.constant.RouteConstant.*;

@RestController
@RequestMapping("/user/staff")
public class StaffController {
    private final StaffService staffService;

    @Autowired
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @Get
    @Role({RoleConstant.STAFF})
    @Permission(USER_STAFF_INDEX)
    public Response<PageResponse<Staff>> getStaff(){
        return new Response<>(new PageResponse<>(staffService.getStaff()));
    }

    @Get("/{id}")
    @Role({RoleConstant.STAFF})
    @Permission(USER_STAFF_SHOW)
    public Response<ModelResponse<Staff>> getStaff(@PathVariable Long id){
        return new Response<>(new ModelResponse<>(staffService.getStaff(id)));
    }

    @Get("/{id}/lookup")
    @Role({RoleConstant.STAFF})
    @Permission(USER_STAFF_ACCOUNT_LOOKUP)
    public Response<ModelResponse<Staff>> lookupStaff(@PathVariable Long id) {
        return new Response<>(new ModelResponse<>(staffService.getStaff(id)));
    }

    @Post
    @Role({RoleConstant.STAFF})
    @Permission(USER_STAFF_CREATE)
    public Response<ModelResponse<Staff>> createStaff(@Valid @RequestBody Request<StaffRequest> request){
        return new Response<>(new ModelResponse<>(staffService.saveStaff(request.getBody().buildModel())));
    }

    @Put("/{id}")
    @Role({RoleConstant.STAFF})
    @Permission(USER_STAFF_UPDATE)
    public Response<ModelResponse<Staff>> updateStaff(@PathVariable Long id, @RequestBody Request<StaffRequest> request){
        Staff staff = staffService.getStaff(id);
        return new Response<>(new ModelResponse<>(staffService.saveStaff(request.getBody().buildModel(staff))));
    }

    @Put("/{id}/toggle")
    @Role({RoleConstant.STAFF})
    @Permission(USER_STAFF_TOGGLE)
    public Response<Staff> toggleStaff(@PathVariable Long id){
        return null;
    }
}
