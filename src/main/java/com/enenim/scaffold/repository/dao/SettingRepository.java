package com.enenim.scaffold.repository.dao;

import com.enenim.scaffold.model.dao.Setting;
import com.enenim.scaffold.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface SettingRepository extends BaseRepository<Setting, Long>{
    @Modifying
    @Query("update Setting s set s._value = ?2 where s._key = ?1")
    Setting updateSettingByKey(String key, String value);

    @Query("select s from Setting s where s._key = ?1")
    Setting findSettingByKey(String key);
}
