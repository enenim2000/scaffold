package com.enenim.scaffold.repository.dao;

import com.enenim.scaffold.model.dao.Vendor;
import com.enenim.scaffold.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface VendorRepository extends BaseRepository<Vendor, Long> {

    @Query("select b from Vendor b where b.email = ?1")
    Optional<Vendor> findByEmail(String email);
}