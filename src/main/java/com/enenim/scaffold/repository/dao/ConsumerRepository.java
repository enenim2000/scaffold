package com.enenim.scaffold.repository.dao;

import com.enenim.scaffold.model.dao.Consumer;
import com.enenim.scaffold.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface ConsumerRepository extends BaseRepository<Consumer, Long> {
    @Query("select c from Consumer c where c.email = ?1")
    Optional<Consumer> findByEmail(String email);
}