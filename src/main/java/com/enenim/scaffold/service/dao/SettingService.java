package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.model.cache.SettingCache;
import com.enenim.scaffold.model.dao.Setting;
import com.enenim.scaffold.repository.dao.SettingRepository;
import com.enenim.scaffold.util.setting.SettingCacheCoreService;
import com.enenim.scaffold.util.setting.SettingCacheCoreUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingService{
    private final SettingRepository settingRepository;
    private final SettingCacheCoreService settingCacheCoreService;

    @Autowired
    public SettingService(SettingRepository settingRepository, SettingCacheCoreService settingCacheCoreService) {
        this.settingRepository = settingRepository;
        this.settingCacheCoreService = settingCacheCoreService;
    }

    public List<Setting> getSettings() {
        return settingRepository.findAll();
    }

    public Setting getSetting(String key) {
        return settingRepository.findSettingByKey(key);
    }

    public Setting saveSetting(Setting setting) {
        setting = settingRepository.updateSettingByKey(setting.getKey(), setting.getValue());
        SettingCache settingCache = SettingCacheCoreUtil.getSettingMap().get(setting.getKey());
        settingCache.setValue(setting.getValue());
        settingCacheCoreService.saveCoreSetting(settingCache);
        return setting;
    }
}