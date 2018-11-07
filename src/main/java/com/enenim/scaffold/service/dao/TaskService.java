package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.model.dao.Task;
import com.enenim.scaffold.repository.dao.TaskRepository;
import com.enenim.scaffold.util.PageRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Page<Task> getTasks(){
        return taskRepository.findAll(PageRequestUtil.getPageRequest());
    }

    public Task getTask(Long id){
        return taskRepository.findOrFail(id);
    }

    public Task saveTask(Task task){
        return taskRepository.save(task);
    }

    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }

    public Task getTaskByRoute(String route){
        return taskRepository.findTaskByRoute(route).orElse(null);
    }

    public Set<Task> findTasksByIds(List<Long> ids){
        return taskRepository.findTasksByIds(ids);
    }
}