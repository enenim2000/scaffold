package com.enenim.scaffold.controller;

import com.enenim.scaffold.annotation.*;
import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.dto.request.GroupRequest;
import com.enenim.scaffold.dto.request.PermissionRequest;
import com.enenim.scaffold.dto.response.CollectionResponse;
import com.enenim.scaffold.dto.response.ModelResponse;
import com.enenim.scaffold.dto.response.PageResponse;
import com.enenim.scaffold.dto.response.Response;
import com.enenim.scaffold.model.dao.Group;
import com.enenim.scaffold.model.dao.Task;
import com.enenim.scaffold.service.dao.GroupService;
import com.enenim.scaffold.service.dao.TaskService;
import com.enenim.scaffold.util.RouteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.enenim.scaffold.constant.RouteConstant.ADMINISTRATION_PERMISSION_SHOW;
import static com.enenim.scaffold.constant.RouteConstant.ADMINISTRATION_PERMISSION_UPDATE;

@RestController
@RequestMapping("/administration/groups")
public class GroupController {

    private final GroupService groupService;
    private final TaskService taskService;

    @Autowired
    public GroupController(GroupService groupService, TaskService taskService) {
        this.groupService = groupService;
        this.taskService = taskService;
    }

    @Get
    public Response <PageResponse<Group>> getGroups() {
        return new Response <>(new PageResponse <>(groupService.getGroups()));
    }

    @Get("/{id}")
    public Response<Group> show(@PathVariable Long id) {
        return new Response <>(groupService.getGroup(id));
    }

    @Post
    public Response <Group> createGroup(@RequestBody GroupRequest request) {
        return new Response <>(groupService.saveGroup(request.buildModel()));
    }

    @Put("/{id}")
    public Response <Group> update(@PathVariable Long id, @RequestBody GroupRequest request) {
        return new Response <>(groupService.saveGroup(request.buildModel(groupService.getGroup(id))));
    }

    @Get("/{id}/tasks")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_PERMISSION_SHOW)
    public Response<CollectionResponse<Task>> getPermission(@PathVariable Long id){
        return new Response<>(new CollectionResponse<>(groupService.getGroup(id).getTasks()));
    }

    @Put("/{id}/tasks")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_PERMISSION_UPDATE)
    public Response<ModelResponse<Group>> updatePermission(@PathVariable Long id, @RequestBody PermissionRequest request){
        List<String> tasks = request.getTasks();

        Set<String> dependentTasks = new HashSet<>();

        for(String task : tasks){
            List<String> childRoutes = RouteUtil.getDependencies(task);
            if(childRoutes.size() > 0){
                dependentTasks.addAll(childRoutes);
            }
        }

        if(!dependentTasks.isEmpty()){
            tasks.addAll(new ArrayList<>(dependentTasks));
        }

        Group group = groupService.getGroup(id);
        group.getTasks().removeAll(group.getTasks());

        if(!tasks.isEmpty()){
            group.getTasks().addAll(taskService.findTasksByRoutes(tasks));
        }

        return new Response<>(new ModelResponse<>(group));
    }

    @Get("/{id}/authorizations")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_PERMISSION_SHOW)
    public Response<CollectionResponse<Task>> getPermissionAuthorizations(@PathVariable Long id){
        return new Response<>(new CollectionResponse<>(groupService.getGroup(id).getAuthorizerTasks()));
    }

    @Put("/{id}/authorizations")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_PERMISSION_UPDATE)
    public Response<ModelResponse<Group>> updatePermissionAuthorizations(@PathVariable Long id, @RequestBody PermissionRequest request){
        Set<Task> tasks = taskService.findTasksByRoutes(request.getTasks());
        Group group = groupService.updatePermissionAuthorizations(tasks, id);
        return new Response<>(new ModelResponse<>(group));
    }
}
