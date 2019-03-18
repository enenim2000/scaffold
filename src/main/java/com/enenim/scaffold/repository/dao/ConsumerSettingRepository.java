package com.enenim.scaffold.repository.dao;

import com.enenim.scaffold.model.dao.ConsumerSetting;
import com.enenim.scaffold.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ConsumerSettingRepository extends BaseRepository<ConsumerSetting, Long> {
    @Query("select cs from ConsumerSetting cs where cs.consumer.id = ?1")
    List<ConsumerSetting> findByConsumerId(Long consumerId);
}