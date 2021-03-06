package com.enenim.scaffold.service.cache;

import com.enenim.scaffold.repository.cache.VendorCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Service(value = "vendorCacheService")
public class VendorCacheService implements VendorCacheRepository{

    /**
     * h => key for caching vendors
     */
    public final static String VENDOR = "Vendor";

    /**
     * h => key for caching vendors grouped by category
     */
    public final static String VENDOR_BY_CATEGORY = "VendorByCategory";

    /**
     * h => key for caching vendor's services grouped by vendor category
     */
    public final static String VENDOR_SERVICE_BY_CATEGORY = "VendorServiceByCategory";

    @Qualifier(value = "redisVendorTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    private HashOperations<String, String, Object> hashOps;

    public VendorCacheService(){}

    @Autowired
    private VendorCacheService(@Qualifier("redisVendorTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    @PostConstruct
    private void init() {
        hashOps = redisTemplate.opsForHash();
    }

    public Object get(String h, String hk) {
        return hashOps.get(h, hk);
    }

    public List<Object> get(String h) {
        return hashOps.values(h);
    }

    public Map<String, Object> getAll(String h) {
        return hashOps.entries(h);
    }

    public void save(String h, String hk, Object hv) {
        hashOps.put(h, hk, hv);
    }

    public void delete(String h, String hk) {
        hashOps.delete(h, hk);
    }

    public void deleteAll(String h) {
        hashOps.delete(h);
    }
}
