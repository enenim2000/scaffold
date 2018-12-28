package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.model.dao.Group;
import com.enenim.scaffold.repository.dao.GroupRepository;
import com.enenim.scaffold.util.PageRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

	private final GroupRepository groupRepository;

	@Autowired
	public PermissionService(GroupRepository groupRepository) {
		this.groupRepository = groupRepository;
	}

	public Page<Group> getPermission() {
		return  groupRepository.findAll(PageRequestUtil.getPageRequest());
	}

	public Group getById(Long permitionid) {
		return groupRepository.getOne(permitionid);
	}

}
