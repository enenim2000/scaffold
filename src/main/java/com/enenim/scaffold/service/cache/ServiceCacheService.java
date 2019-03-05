package com.enenim.scaffold.service.cache;

import com.enenim.scaffold.model.dao.Service;
import com.enenim.scaffold.util.JsonConverter;
import com.google.common.reflect.TypeToken;
import org.springframework.scheduling.annotation.Async;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public class ServiceCacheService {

    private static final String KEY = "Service";

    private final SharedCacheService sharedCacheService;

    public ServiceCacheService(SharedCacheService sharedCacheService) {
        this.sharedCacheService = sharedCacheService;
    }

    public List<Service> getServicesByCategoryAsList(String categoryKey){
        String json = (String) sharedCacheService.get(KEY, categoryKey + ":list");
        return JsonConverter.getGson().fromJson(json, new TypeToken<List<Service>>(){}.getType());
    }

    public Map<String, Service> getServicesByCategoryAsMap(String categoryKey){
        String json = (String) sharedCacheService.get(KEY, categoryKey + ":map");
        return JsonConverter.getGson().fromJson(json, new TypeToken<HashMap<String, Service>>() {}.getType());
    }

    @Async
    public void saveServicesAsList(String categoryKey, List<Service> services){
        sharedCacheService.save(KEY, categoryKey + ":list", JsonConverter.getJson(services));
    }

    @Async
    public void saveServicesAsMap(String categoryKey, List<Service> services){
        Map<Long, Service> serviceMap = new HashMap<>();
        for(Service service : services){
            serviceMap.put(service.getId(), service);
        }
        sharedCacheService.save(KEY, categoryKey + ":map", JsonConverter.getJson(serviceMap));
    }

    public List<Service> getServicesByConsumerAsList(Long consumerId){
        String json = (String) sharedCacheService.get(KEY, consumerId + ":consumer:list");
        return JsonConverter.getGson().fromJson(json, new TypeToken<List<Service>>(){}.getType());
    }

    public Map<String, Service> getServicesByConsumerAsMap(Long consumerId){
        String json = (String) sharedCacheService.get(KEY, consumerId + ":consumer:map");
        return JsonConverter.getGson().fromJson(json, new TypeToken<HashMap<String, Service>>() {}.getType());
    }

    @Async
    public void saveConsumerServicesAsList(Long consumerId, List<Service> services){
        sharedCacheService.save(KEY, consumerId + ":consumer:list", JsonConverter.getJson(services));
    }

    @Async
    public void saveConsumerServicesAsMap(Long consumerId, List<Service> services){
        Map<Long, Service> serviceMap = new HashMap<>();
        for(Service service : services){
            serviceMap.put(service.getId(), service);
        }
        sharedCacheService.save(KEY, consumerId + ":consumer:map", JsonConverter.getJson(serviceMap));
    }
}
