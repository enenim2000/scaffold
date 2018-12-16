package com.enenim.scaffold.repository.cache;

import com.enenim.scaffold.model.cache.LoginCache;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface LoginCacheRepository extends CacheRepository<Map<String, LoginCache>, String>{
    void save(LoginCache data);
    void delete(Long id, String sessionId);
}
