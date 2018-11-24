package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.model.dao.Group;
import com.enenim.scaffold.model.dao.Task;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
public class PermissionRequest extends RequestBody<Group>{

    private List<Long> tasks;

    public Group authorizeTaskList(Group group) {
        Set<Task> tasklist = new HashSet<>();
        for (Long id : tasks) {
            Task task = new Task(id);
            tasklist.add(task);
        }
        group.setAuthorizerTasks(tasklist);
        return group;
    }

    @Override
    public Group buildModel() {
        Group group = new Group();
        Set<Task> tasks = new HashSet<>();
        for (Long id : this.tasks) {
            Task task = new Task(id);
            tasks.add(task);
        }
        group.setTasks(tasks);
        return group;
    }

    @Override
    public Group buildModel(Group group) {
        Set<Task> tasks = new HashSet<>();
        for (Long id : this.tasks) {
            Task task = new Task(id);
            tasks.add(task);
        }
        group.setTasks(tasks);
        return group;
    }
}
