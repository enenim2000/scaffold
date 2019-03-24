package com.enenim.scaffold.dto.response;

import com.enenim.scaffold.model.BaseModel;
import com.enenim.scaffold.model.dao.Group;
import com.enenim.scaffold.model.dao.Task;
import com.enenim.scaffold.util.ObjectMapperUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class GroupResponse extends BaseModel {
    
    private Set<Task> authorizerTasks = new HashSet<>();

    public GroupResponse(Group group) {
        ObjectMapperUtil.map(group, this);
        this.setAuthorizerTasks(group.getAuthorizerTasks());
    }
}
