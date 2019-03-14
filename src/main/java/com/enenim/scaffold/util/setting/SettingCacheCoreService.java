package com.enenim.scaffold.util.setting;

import com.enenim.scaffold.model.dao.Setting;
import com.enenim.scaffold.service.dao.SettingService;
import com.enenim.scaffold.util.SettingConfigUtil;
import com.enenim.scaffold.util.SettingListCategory;
import com.enenim.scaffold.util.SystemSetting;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SettingCacheCoreService {
    private SettingService settingService;

    public SettingCacheCoreService(SettingService settingService) {
        this.settingService = settingService;
    }

    public Boolean multipleSessionIsEnabled() {
        String value = SettingConfigUtil.getSystemSetting("enable_multiple_login").getDetail().getValue();
        return value.equalsIgnoreCase("yes");
    }

    public SystemSetting getSystemSetting(String key){
        return SettingConfigUtil.getSystemSetting(key);
    }

    public List<SettingListCategory> getSystemSettings(){
        return SettingConfigUtil.getSettingAsList();
    }

    public SystemSetting updateSystemSetting(Setting setting){
        return SettingConfigUtil.updateSystemSetting(setting);
    }

    public boolean syncSettings(){
        List<Setting> dbSettings = settingService.getSettings();

        Map<String, Setting> dbSettingsMap = new HashMap<>();

        for(Setting setting : dbSettings){
            dbSettingsMap.put(setting.getSettingKey(), setting);
        }

        List<Setting> cacheSettings = SettingConfigUtil.getSettings();

        List<Setting> newSettings = new ArrayList<>();

        for(Setting setting : cacheSettings){
            if(!dbSettingsMap.containsKey(setting.getSettingKey())){
                newSettings.add(setting);
            }
        }

        if(!newSettings.isEmpty()){
            newSettings = settingService.saveSettings(newSettings);
        }

        return newSettings.size() > 0;
    }
}