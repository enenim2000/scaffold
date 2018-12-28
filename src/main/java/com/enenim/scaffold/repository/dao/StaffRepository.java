package com.enenim.scaffold.repository.dao;

import com.enenim.scaffold.model.dao.Staff;
import com.enenim.scaffold.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface StaffRepository extends BaseRepository<Staff, Long> {
}