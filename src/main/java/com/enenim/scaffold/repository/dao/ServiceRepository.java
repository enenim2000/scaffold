package com.enenim.scaffold.repository.dao;

import com.enenim.scaffold.model.dao.Service;
import com.enenim.scaffold.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ServiceRepository extends BaseRepository<Service, Long> {

    @Query("select s from Service s where s.vendor.id = ?1")
    Page<Service> findVendorServices(Long vendorId, Pageable pageable);
}