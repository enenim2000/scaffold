package com.enenim.scaffold.repository.dao;

import com.enenim.scaffold.model.dao.Tracker;
import com.enenim.scaffold.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackerRepository extends BaseRepository<Tracker, Long> {
}