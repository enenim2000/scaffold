package com.enenim.scaffold.repository.dao;

import com.enenim.scaffold.enums.VerifyStatus;
import com.enenim.scaffold.model.dao.Login;
import com.enenim.scaffold.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface LoginRepository extends BaseRepository<Login, Long> {
    @Query("select l from Login l where l.username = ?1")
    Optional<Login> findByUsername(String username);

    @Modifying
    @Query("UPDATE Login l SET l.verifyStatus = ?1 WHERE l.username = ?2")
    void updateVerifyStatus(VerifyStatus status, String username);
}
