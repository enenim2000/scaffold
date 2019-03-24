package com.enenim.scaffold.repository.dao;

import com.enenim.scaffold.model.dao.Service;
import com.enenim.scaffold.model.dao.ServiceForm;
import com.enenim.scaffold.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ServiceFormRepository extends BaseRepository<ServiceForm, Long> {

    @Query("select sf.service from ServiceForm sf where sf.transaction.transactionReference = ?1")
    List<Service> getServiceByReference(String transactionReference);
}