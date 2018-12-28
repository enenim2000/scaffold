package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.model.dao.Group;
import com.enenim.scaffold.model.dao.Staff;
import com.enenim.scaffold.model.dao.Task;
import com.enenim.scaffold.repository.dao.GroupRepository;
import com.enenim.scaffold.repository.dao.StaffRepository;
import com.enenim.scaffold.util.PageRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GroupService {

	private final GroupRepository groupRepository;

	private final StaffRepository staffRepository;

	@Autowired
	public GroupService(GroupRepository groupRepository, StaffRepository staffRepository) {
		this.groupRepository = groupRepository;
		this.staffRepository = staffRepository;
	}

	public Page<Group> getGroups() {
		return groupRepository.findAll(PageRequestUtil.getPageRequest());
	}

	public Page<Group> getEnabledGroups() {
		return groupRepository.findAllEnabled(PageRequestUtil.getPageRequest());
	}

	public Group updatePermission(Set<Task> tasks, Long id) {
		Group group = this.getGroup(id);
		group.getTasks().removeAll(group.getTasks());
		group.setTasks(tasks);
		return groupRepository.save(group);
	}

	public Group updatePermissionAuthorizations(Set<Task> tasks, Long id) {
		Group group = this.getGroup(id);
		group.getAuthorizerTasks().removeAll(group.getAuthorizerTasks());
		group.setAuthorizerTasks(tasks);
		return groupRepository.save(group);
	}

	public Group saveGroup(Group group) {
		return groupRepository.save(group);
	}

	public Staff getStaffById(Long id) {
		return staffRepository.findOrFail(id);
	}

	public Group getGroup(long id) {
		return groupRepository.findOrFail(id);
	}
}
