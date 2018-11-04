package com.enenim.scaffold.repository.cache;

import com.enenim.scaffold.model.cache.SettingCache;

import java.util.List;
import java.util.Map;

public interface GenericCacheRepository<T, ID>{
   T get(String h, ID hk);
   List<T> get(String h);
   Map<ID, T> getAll(String h);
   void save(String hk, T hv);
   void save(String key, Map<String, SettingCache> data);
   void delete(String h, ID hk);
   void deleteAll(String h);
}