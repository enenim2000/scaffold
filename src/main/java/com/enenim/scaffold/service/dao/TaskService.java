package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.model.dao.Task;
import com.enenim.scaffold.repository.dao.TaskRepository;
import com.enenim.scaffold.util.CommonUtil;
import com.enenim.scaffold.util.PageRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

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

    public boolean syncTask(RequestMappingHandlerMapping requestMappingHandlerMapping){
        Map<RequestMappingInfo, HandlerMethod> endpoints = requestMappingHandlerMapping.getHandlerMethods();
        Iterator it = endpoints.entrySet().iterator();
        HandlerMethod handlerMethod;
        String role;
        List<String> routes = new ArrayList<>();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            handlerMethod = (HandlerMethod) pair.getValue();
            if(handlerMethod.hasMethodAnnotation(PreAuthorize.class)){
                role = handlerMethod.getMethod().getDeclaredAnnotation(PreAuthorize.class).value();
                if(!routes.contains(role)){
                    routes.add(role);
                }
            }
        }

        List<Task> dBTasks = this.taskRepository.findAll();
        List<String> notDeletedTasksRoutes = new ArrayList<>();
        dBTasks.forEach((task) -> {
            if(!routes.contains(task.getRoute())){
                this.deleteTask(task.getId());
            }else {
                notDeletedTasksRoutes.add(task.getRoute());
            }
        });

        List<String> newRoutes = CommonUtil.difference(routes, notDeletedTasksRoutes);
        List<Task> newTasks = new ArrayList<>();
        newRoutes.forEach((newRoute)->{
            Task task = new Task();
            String[] routeDetails = newRoute.split("\\.");
            task.setRoute(newRoute);
            task.setName(CommonUtil.capitaliseFirstLetter(routeDetails[1]) + " " + CommonUtil.capitaliseFirstLetter(routeDetails[2]));
            task.setDescription(CommonUtil.capitaliseFirstLetter(routeDetails[2]));
            task.setModule(CommonUtil.capitaliseFirstLetter(routeDetails[0]));
            newTasks.add(task);
        });

        return this.taskRepository.saveAll(newTasks).size() > 0;
    }

    public Task getTaskByRoute(String route){
        return taskRepository.findTaskByRoute(route).orElse(null);
    }

    public Set<Task> findTasksByIds(List<Long> ids){
        return taskRepository.findTasksByIds(ids);
    }

    public Set<Task> findTasksByRoutes(List<String> routes){
        return taskRepository.findTasksByRoutes(routes);
    }
}