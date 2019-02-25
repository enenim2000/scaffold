package com.enenim.scaffold.service.cache;

import com.enenim.scaffold.model.dao.Vendor;
import com.enenim.scaffold.util.JsonConverter;
import com.google.common.reflect.TypeToken;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VendorCacheService {

    private static final String KEY = "Vendor";

    private final SharedCacheService sharedCacheService;

    public VendorCacheService(SharedCacheService sharedCacheService) {
        this.sharedCacheService = sharedCacheService;
    }

    public List<Vendor> getVendorsByCategoryAsList(String categoryKey){
        String json = (String) sharedCacheService.get(KEY, categoryKey);
        return JsonConverter.getGson().fromJson(json, new TypeToken<List<Vendor>>(){}.getType());
    }

    public Map<String, Vendor> getVendorsByCategoryAsMap(String categoryKey){
        String json = (String) sharedCacheService.get(KEY, categoryKey);
        return JsonConverter.getGson().fromJson(json, new TypeToken<HashMap<String, Vendor>>() {}.getType());
    }

    @Async
    public void saveVendorsAsList(String categoryKey, List<Vendor> vendors){
        Map<Long, Vendor> vendorMap = new HashMap<>();
        for(Vendor vendor : vendors){
            vendorMap.put(vendor.getId(), vendor);
        }
        sharedCacheService.save(KEY, categoryKey, JsonConverter.getJson(vendorMap));
    }

    @Async
    public void saveVendorsAsMap(String categoryKey, List<Vendor> vendors){
        sharedCacheService.save(KEY, categoryKey, JsonConverter.getJson(vendors));
    }
}
