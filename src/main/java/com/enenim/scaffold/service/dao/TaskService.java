package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.annotation.Permission;
import com.enenim.scaffold.model.dao.Task;
import com.enenim.scaffold.repository.dao.TaskRepository;
import com.enenim.scaffold.util.CommonUtil;
import com.enenim.scaffold.util.PageRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
            if(handlerMethod.hasMethodAnnotation(Permission.class)){
                role = handlerMethod.getMethod().getDeclaredAnnotation(Permission.class).value();
                if(!routes.contains(role)){
                    routes.add(role);
                }
            }
        }

        List<Task> dBTasks = this.taskRepository.findAll();
        List<String> notDeletedTasksRoutes = new ArrayList<>();
        List<Task> deleteTasks = new ArrayList<>();
        dBTasks.forEach((task) -> {
            if(!routes.contains(task.getRoute())){
                //Delete only existing routes that has not being used
                //Commented bcos once route/task is created it should not be removed
                //this.deleteTask(task.getId());
            }else {
                notDeletedTasksRoutes.add(task.getRoute());
            }
        });

        Collection<String> newRoutes = CommonUtil.difference(routes, notDeletedTasksRoutes);

        if(!deleteTasks.isEmpty()){
            this.taskRepository.deleteInBatch(deleteTasks);
        }

        List<Task> newTasks = new ArrayList<>();

        newRoutes.forEach((newRoute)->{
            Task task = new Task();
            String[] routeDetails = newRoute.split("\\.");
            task.setRoute(newRoute);
            String name = CommonUtil.capitaliseFirstLetter(routeDetails[1]) + " " + CommonUtil.capitaliseFirstLetter(routeDetails[2]);
            task.setName(name);
            task.setDescription(name);
            task.setModule(CommonUtil.capitaliseFirstLetter(routeDetails[0]));
            task.setOrder(1);
            task.setParentTaskId(0L);
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