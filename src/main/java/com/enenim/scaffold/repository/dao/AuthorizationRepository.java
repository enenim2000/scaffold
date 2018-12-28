package com.enenim.scaffold.repository.dao;

import com.enenim.scaffold.enums.AuthorizationStatus;
import com.enenim.scaffold.model.dao.Authorization;
import com.enenim.scaffold.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface AuthorizationRepository extends BaseRepository<Authorization, Long> {
    @Query("select a from #{#entityName} a where a.status = ?1")
    Page<Authorization> findAuthorizationByType(AuthorizationStatus status, Pageable pageable);

    @Query("select a from #{#entityName} a where a.status in (com.enenim.scaffold.enums.AuthorizationStatus.REJECTED, com.enenim.scaffold.enums.AuthorizationStatus.FORWARDED, com.enenim.scaffold.enums.AuthorizationStatus.PENDING)")
    Page<Authorization> findAuthorizationByAll(Pageable pageable);

    @Query("select a from #{#entityName} a where a.status = ?1 and a.status in (com.enenim.scaffold.enums.AuthorizationStatus.REJECTED, com.enenim.scaffold.enums.AuthorizationStatus.FORWARDED, com.enenim.scaffold.enums.AuthorizationStatus.PENDING)")
    Page<Authorization> findAuthorizationByAllAndType(AuthorizationStatus status, Pageable pageable);

    @Query("select a from #{#entityName} a where a.staff.id = ?1")
    Page<Authorization> findAuthorizationByMe(Long id, Pageable pageable);

    @Query("select a from #{#entityName} a where a.staff.id = ?1 and a.status = ?2")
    Page<Authorization> findAuthorizationByMeAndType(Long id, AuthorizationStatus status, Pageable pageable);

    @Query("select a from #{#entityName} a where a.staff.id = ?1 and a.status in (com.enenim.scaffold.enums.AuthorizationStatus.AUTHORIZED, com.enenim.scaffold.enums.AuthorizationStatus.REJECTED)")
    Page<Authorization> findAuthorizationByMeAndTypes(Long id, Pageable pageable);

    @Query("select a from #{#entityName} a where a.status = ?1 and a.status in (com.enenim.scaffold.enums.AuthorizationStatus.PENDING, com.enenim.scaffold.enums.AuthorizationStatus.FORWARDED)")
    Page<Authorization> findAuthorizationByCheckedAndType(AuthorizationStatus status, Pageable pageable);

    @Query("select a from #{#entityName} a where a.status in (com.enenim.scaffold.enums.AuthorizationStatus.PENDING , com.enenim.scaffold.enums.AuthorizationStatus.FORWARDED)")
    Page<Authorization> findAuthorizationByChecked(Pageable pageable);
}