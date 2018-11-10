package com.enenim.scaffold.controller;

import com.enenim.scaffold.annotation.Get;
import com.enenim.scaffold.annotation.Post;
import com.enenim.scaffold.annotation.Put;
import com.enenim.scaffold.dto.request.GroupRequest;
import com.enenim.scaffold.dto.request.Request;
import com.enenim.scaffold.dto.response.PageResponse;
import com.enenim.scaffold.dto.response.Response;
import com.enenim.scaffold.model.dao.Group;
import com.enenim.scaffold.service.dao.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/administration/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Get
    public Response <PageResponse<Group>> getGroups() {
        return new Response <>(new PageResponse <>(groupService.getGroups()));
    }

    @Get("/{id}")
    public Response<Group> show(@PathVariable Long id) {
        return new Response <>(groupService.getGroup(id));
    }

    @Post
    public Response <Group> createGroup(@RequestBody Request<GroupRequest> request) {
        return new Response <>(groupService.saveGroup(request.getBody().buildModel()));
    }

    @Put("/{id}")
    public Response <Group> update(@PathVariable Long id, @RequestBody Request<GroupRequest> request) {
        return new Response <>(groupService.saveGroup(request.getBody().buildModel(groupService.getGroup(id))));
    }
}
