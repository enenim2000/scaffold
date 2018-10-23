package com.enenim.scaffold.service;

import com.enenim.scaffold.repository.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;


@SuppressWarnings("SpringJavaAutowiringInspection")
public class BaseRepositoryService<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    public BaseRepositoryService(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(entityInformation.getJavaType(), entityManager), entityManager);
    }

    public BaseRepositoryService(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
    }

    public T findOrFail(ID id) {
        return null;
    }

    public T find(ID id) {
        return null;
    }

    @Transactional
    @Override
    public List<T> search(Pageable pageable) {
        return null;
    }

    @Transactional
    public Object toggle(Long id){
        return null;
    }

    @Transactional
    public Object toggle(Long id, boolean skipAudit){
        return null;
    }

    @Transactional
    public Object toggle(Long id, boolean skipAudit, boolean skipAuthorization){
        return null;
    }
}

