package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.model.dao.Group;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PermissionRequest extends RequestBody<Group>{

    private List<String> tasks;

    @Override
    public Group buildModel() {
        return new Group();
    }

    @Override
    public Group buildModel(Group group) {
        return group;
    }
}
