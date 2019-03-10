package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.model.dao.Setting;
import com.enenim.scaffold.repository.dao.SettingRepository;
import com.enenim.scaffold.util.SettingConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingService{
    private final SettingRepository settingRepository;

    @Autowired
    public SettingService(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    public List<Setting> getSettings() {
        return settingRepository.findAll();
    }

    public Setting getSetting(String key) {
        return settingRepository.findSettingByKey(key);
    }

    public Setting getSetting(Long id) {
        return settingRepository.findOrFail(id);
    }

    public Setting saveSetting(Setting setting) {
        setting = settingRepository.save(setting);
        SettingConfigUtil.updateSystemSetting( settingRepository.save(setting) );
        return setting;
    }
}