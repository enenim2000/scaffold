package com.enenim.scaffold.repository.cache;

import java.util.List;
import java.util.Map;

public interface CacheRepository <T, ID>{
   T get(ID id);
   List<T> get();
   Map<ID, T> getAll();
   void save(T data);
   void delete(ID id);
}