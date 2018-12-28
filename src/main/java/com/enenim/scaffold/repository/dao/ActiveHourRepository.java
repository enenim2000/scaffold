package com.enenim.scaffold.repository.dao;

import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.model.dao.ActiveHour;
import com.enenim.scaffold.repository.BaseRepository;
import com.enenim.scaffold.shared.IdName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ActiveHourRepository extends BaseRepository<ActiveHour, Long> {

    @Query("select activeHour from #{#entityName} activeHour where activeHour.enabled = ?1")
    Page<ActiveHour> findActiveHoursByStatus(EnabledStatus status, Pageable pageable);

    @Query("select new com.enenim.scaffold.shared.IdName(activeHour.id, activeHour.name) from #{#entityName} activeHour where activeHour.enabled = com.enenim.scaffold.enums.EnabledStatus.ENABLED")
    List<IdName> findEnabledActiveHours();
}