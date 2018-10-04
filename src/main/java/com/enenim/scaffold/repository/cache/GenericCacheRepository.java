package com.enenim.scaffold.repository.cache;

import java.util.List;
import java.util.Map;

public interface GenericCacheRepository<T, ID>{
   T get(String h, ID hk);
   List<T> get(String h);
   Map<ID, T> getAll(String h);
   void save(String h, String hk, T hv);
   void delete(String h, ID hk);
   void deleteAll(String h);
}