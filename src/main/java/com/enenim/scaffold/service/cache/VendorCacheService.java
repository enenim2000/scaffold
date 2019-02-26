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
        String json = (String) sharedCacheService.get(KEY, categoryKey + ":list");
        return JsonConverter.getGson().fromJson(json, new TypeToken<List<Vendor>>(){}.getType());
    }

    public Map<String, Vendor> getVendorsByCategoryAsMap(String categoryKey){
        String json = (String) sharedCacheService.get(KEY, categoryKey + ":map");
        return JsonConverter.getGson().fromJson(json, new TypeToken<HashMap<String, Vendor>>() {}.getType());
    }

    @Async
    public void saveVendorsAsList(String categoryKey, List<Vendor> vendors){
        sharedCacheService.save(KEY, categoryKey + ":list", JsonConverter.getJson(vendors));
    }

    @Async
    public void saveVendorsAsMap(String categoryKey, List<Vendor> vendors){
        Map<Long, Vendor> vendorMap = new HashMap<>();
        for(Vendor vendor : vendors){
            vendorMap.put(vendor.getId(), vendor);
        }
        sharedCacheService.save(KEY, categoryKey + ":map", JsonConverter.getJson(vendorMap));
    }

    public List<Vendor> getVendorsByConsumerAsList(Long consumerId){
        String json = (String) sharedCacheService.get(KEY, consumerId + ":consumer:list");
        return JsonConverter.getGson().fromJson(json, new TypeToken<List<Vendor>>(){}.getType());
    }

    public Map<String, Vendor> getVendorsByConsumerAsMap(Long consumerId){
        String json = (String) sharedCacheService.get(KEY, consumerId + ":consumer:map");
        return JsonConverter.getGson().fromJson(json, new TypeToken<HashMap<String, Vendor>>() {}.getType());
    }

    @Async
    public void saveConsumerVendorsAsList(Long consumerId, List<Vendor> vendors){
        sharedCacheService.save(KEY, consumerId + ":consumer:list", JsonConverter.getJson(vendors));
    }

    @Async
    public void saveConsumerVendorsAsMap(Long consumerId, List<Vendor> vendors){
        Map<Long, Vendor> vendoreMap = new HashMap<>();
        for(Vendor vendor : vendors){
            vendoreMap.put(vendor.getId(), vendor);
        }
        sharedCacheService.save(KEY, consumerId + ":consumer:map", JsonConverter.getJson(vendoreMap));
    }
}
