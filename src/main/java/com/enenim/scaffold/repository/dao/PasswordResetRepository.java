package com.enenim.scaffold.repository.dao;

import com.enenim.scaffold.model.dao.PasswordReset;
import com.enenim.scaffold.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PasswordResetRepository extends BaseRepository<PasswordReset, Long> {


}
