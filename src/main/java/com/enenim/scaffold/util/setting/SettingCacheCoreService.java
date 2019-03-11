package com.enenim.scaffold.util.setting;

import com.enenim.scaffold.util.SettingConfigUtil;
import com.enenim.scaffold.util.SettingListCategory;
import com.enenim.scaffold.util.SystemSetting;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingCacheCoreService {

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
}