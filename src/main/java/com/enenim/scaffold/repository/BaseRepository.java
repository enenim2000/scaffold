package com.enenim.scaffold.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
    List<T> search();

    List<T> search(Pageable pageable);

    T findOrFail(ID id);

    T find(ID id);

    Object toggle(Long id);

    Object toggle(Long id, boolean skipAudit);

    Object toggle(Long id, boolean skipAudit, boolean skipAuthorization);
}