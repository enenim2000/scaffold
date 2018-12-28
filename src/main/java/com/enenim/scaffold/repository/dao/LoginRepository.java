package com.enenim.scaffold.repository.dao;

import com.enenim.scaffold.model.dao.Login;
import com.enenim.scaffold.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface LoginRepository extends BaseRepository<Login, Long> {
    @Query("select login from Login login where login.username = ?1")
    Optional<Login> findByUsername(String username);
}
