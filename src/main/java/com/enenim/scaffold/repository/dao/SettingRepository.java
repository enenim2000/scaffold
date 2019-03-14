package com.enenim.scaffold.repository.dao;

import com.enenim.scaffold.model.dao.Setting;
import com.enenim.scaffold.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface SettingRepository extends BaseRepository<Setting, Long>{

    @Query("select s from Setting s where s.settingKey = ?1")
    Setting findSettingByKey(String key);

}
