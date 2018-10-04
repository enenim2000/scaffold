package com.enenim.scaffold.repository.cache;

import org.springframework.stereotype.Repository;

@Repository
public interface SharedCacheRepository extends GenericCacheRepository<Object, String>{

}
