package com.enenim.scaffold.repository.dao;

import com.enenim.scaffold.model.dao.Group;
import com.enenim.scaffold.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface GroupRepository extends BaseRepository<Group, Long> {
    @Query("SELECT new com.enenim.scaffold.dto.response.GroupResponse(g) FROM Group g WHERE g.enabled = com.enenim.scaffold.enums.EnabledStatus.ENABLED")
    Page<Group> findAllEnabled(Pageable pageable);

    @Query("SELECT g FROM Group g WHERE g.enabled = com.enenim.scaffold.enums.EnabledStatus.ENABLED and g.id = ?1")
    Group findAuthorizerEnabled(Long id);
}
