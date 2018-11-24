package com.enenim.scaffold.controller;

import com.enenim.scaffold.annotation.Delete;
import com.enenim.scaffold.annotation.Get;
import com.enenim.scaffold.dto.response.PageResponse;
import com.enenim.scaffold.dto.response.Response;
import com.enenim.scaffold.model.dao.Task;
import com.enenim.scaffold.service.dao.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "administrationTaskController")
@RequestMapping("/administration/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Get
    public Response<PageResponse<Task>> getTasks() {
        return new Response<>(new PageResponse<>(taskService.getTasks()));
    }

    @Get("/{id}")
    public Response<Task> getTask(@PathVariable Long id){
        return new Response<>(taskService.getTask(id));
    }

    @Delete("/{id}")
    public Response<Task> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return null;
    }
}