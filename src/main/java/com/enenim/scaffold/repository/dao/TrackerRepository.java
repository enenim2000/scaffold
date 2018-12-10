package com.enenim.scaffold.repository.dao;

import com.enenim.scaffold.model.dao.Tracker;
import com.enenim.scaffold.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrackerRepository extends BaseRepository<Tracker, Long> {
    @Query("select tracker from Tracker tracker where tracker.sessionId = ?1")
    Optional<Tracker> findBySessionId(String sessionId);
}