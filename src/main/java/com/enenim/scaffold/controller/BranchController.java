package com.enenim.scaffold.controller;

import com.enenim.scaffold.annotation.*;
import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.dto.request.Request;
import com.enenim.scaffold.dto.request.BranchRequest;
import com.enenim.scaffold.dto.response.ModelResponse;
import com.enenim.scaffold.dto.response.PageResponse;
import com.enenim.scaffold.dto.response.Response;
import com.enenim.scaffold.model.dao.Branch;
import com.enenim.scaffold.service.dao.BranchService;
import com.enenim.scaffold.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.enenim.scaffold.constant.RouteConstant.*;

@RestController
@RequestMapping("/branches")
public class BranchController {
    private final BranchService branchService;

    @Autowired
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @Get
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_BRANCH_INDEX)
    public Response<PageResponse<Branch>> getBranches(){
        return new Response<>(new PageResponse<>(branchService.getBranches()));
    }

    @Get("/{id}")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_BRANCH_SHOW)
    public Response<ModelResponse<Branch>> getBranch(@PathVariable Long id){
        return new Response<>(new ModelResponse<>(branchService.getBranch(id)));
    }

    @Post
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_BRANCH_CREATE)
    public Response<ModelResponse<Branch>> createBranch(@Valid @RequestBody BranchRequest request){
        return new Response<>(new ModelResponse<>(branchService.saveBranch(request.buildModel())));
    }

    @Put("/{id}")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_BRANCH_UPDATE)
    public Response<ModelResponse<Branch>> updateBranch(@PathVariable Long id, @RequestBody BranchRequest request){
        Branch branch = branchService.getBranch(id);
        return new Response<>(new ModelResponse<>(branchService.saveBranch(request.buildModel(branch))));
    }
}
