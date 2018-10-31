package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.model.dao.Staff;
import com.enenim.scaffold.repository.dao.StaffRepository;
import com.enenim.scaffold.util.PageRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class StaffService {

    private final StaffRepository staffRepository;

    @Autowired
    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public Page<Staff> getStaff() {
        return staffRepository.findAll(PageRequestUtil.getPageRequest());
    }

    public Staff getStaff(Long id) {
        return staffRepository.findOrFail(id);
    }

    public Staff saveStaff(Staff staff) {
        return staffRepository.save(staff);
    }

}
