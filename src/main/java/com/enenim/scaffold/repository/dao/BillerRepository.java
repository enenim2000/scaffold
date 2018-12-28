package com.enenim.scaffold.repository.dao;

import com.enenim.scaffold.model.dao.Biller;
import com.enenim.scaffold.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface BillerRepository extends BaseRepository<Biller, Long> {

    @Query("select b from Biller b where b.email = ?1")
    Optional<Biller> findByEmail(String email);
}