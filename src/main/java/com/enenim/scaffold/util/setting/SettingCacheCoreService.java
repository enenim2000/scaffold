package com.enenim.scaffold.util.setting;

import com.enenim.scaffold.model.cache.SettingCache;
import com.enenim.scaffold.util.SettingConfigUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SettingCacheCoreService {

    public Boolean multipleSessionIsEnabled() {
        String value = SettingConfigUtil.getSystemSetting("enable_multiple_login").getDetail().getValue();
        return value.equalsIgnoreCase("yes");
    }

    @Async
    public void saveCoreSetting(SettingCache setting){

    }

    @Async
    public void saveCoreSetting(Map<String, SettingCache> settings){

    }

    @Async
    public void deleteCoreSetting(String id){

    }
}