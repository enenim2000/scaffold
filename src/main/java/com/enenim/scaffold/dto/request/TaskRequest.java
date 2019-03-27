package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.model.dao.Task;
import com.enenim.scaffold.util.ObjectMapperUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class TaskRequest extends RequestBody<Task>{

    @NotBlank(message = "@{task.module.required}")
    private String module;

    @NotBlank(message = "@{task.name.required}")
    private String name;

    @NotBlank(message = "@{task.description.required}")
    private String description;

    @Override
    public Task buildModel() {
        return ObjectMapperUtil.map(this, Task.class);
    }

    @Override
    public Task buildModel(Task task) {
        return ObjectMapperUtil.map(this, task);
    }
}
