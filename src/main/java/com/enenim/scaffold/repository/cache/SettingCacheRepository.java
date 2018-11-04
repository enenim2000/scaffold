package com.enenim.scaffold.repository.cache;

import com.enenim.scaffold.model.cache.SettingCache;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingCacheRepository extends GenericCacheRepository<SettingCache, String>{

}
