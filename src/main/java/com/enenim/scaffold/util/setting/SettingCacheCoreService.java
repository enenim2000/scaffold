package com.enenim.scaffold.util.setting;

import com.enenim.scaffold.model.cache.SettingCache;
import com.enenim.scaffold.service.cache.SettingCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SettingCacheCoreService {

    private static final String KEY = "Core";

    private final SettingCacheService settingCacheService;

    @Autowired
    public SettingCacheCoreService(SettingCacheService settingCacheService) {
        this.settingCacheService = settingCacheService;
    }

    public List<SettingCache> getCoreSettings(){
        return settingCacheService.get(KEY);
    }

    public SettingCache getCoreSetting(String id){
        return settingCacheService.get(KEY, id);
    }

    @Async
    public void saveCoreSetting(SettingCache setting){
        settingCacheService.save(KEY, setting);
    }

    @Async
    public void saveCoreSetting(Map<String, SettingCache> settings){
        settingCacheService.save(KEY, settings);
    }

    @Async
    public void deleteCoreSetting(String id){
        settingCacheService.delete(KEY, id);
    }
}