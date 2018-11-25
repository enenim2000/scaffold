package com.enenim.scaffold.controller;

import com.enenim.scaffold.annotation.Get;
import com.enenim.scaffold.annotation.Permission;
import com.enenim.scaffold.annotation.Put;
import com.enenim.scaffold.annotation.Role;
import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.dto.request.Request;
import com.enenim.scaffold.dto.request.TaskRequest;
import com.enenim.scaffold.dto.response.BooleanResponse;
import com.enenim.scaffold.dto.response.ModelResponse;
import com.enenim.scaffold.dto.response.PageResponse;
import com.enenim.scaffold.dto.response.Response;
import com.enenim.scaffold.model.dao.Task;
import com.enenim.scaffold.service.dao.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.enenim.scaffold.constant.RouteConstant.*;

@RestController(value = "administrationTaskController")
@RequestMapping("/administration/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Get
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_TASK_INDEX)
    public Response<PageResponse<Task>> getTasks() {
        return new Response<>(new PageResponse<>(taskService.getTasks()));
    }

    @Get("/{id}")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_TASK_SHOW)
    public Response<ModelResponse<Task>> getTask(@PathVariable Long id){
        return new Response<>(new ModelResponse<>(taskService.getTask(id)));
    }

    @Put("/{id}")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_TASK_UPDATE)
    public Response<ModelResponse<Task>> updateTask(@PathVariable Long id, @Valid @RequestBody Request<TaskRequest> request) {
        Task task = taskService.getTask(id);
        return new Response<>(new ModelResponse<>(taskService.saveTask(request.getBody().buildModel(task))));
    }

    @Put("/sync")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_TASK_SYNC)
    public Response<BooleanResponse> syncTasks() {
        return new Response<>(new BooleanResponse(taskService.syncTask()));
    }
}