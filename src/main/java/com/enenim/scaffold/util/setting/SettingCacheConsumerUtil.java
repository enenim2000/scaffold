package com.enenim.scaffold.util.setting;

import com.enenim.scaffold.model.cache.SettingCache;
import com.enenim.scaffold.shared.IdName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingCacheConsumerUtil {

    private static final List<IdName> SETTING_CATEGORIES = new ArrayList<IdName>(){{
        add( new IdName(1L,  "Personal Consumer Protection") );
        add( new IdName(2L,  "Transaction Preferences") );
    }};


    private static final List<SettingCache> SETTINGS = new ArrayList<SettingCache>(){{
        add( new SettingCache(category(0), "max_trans", "Daily Transaction Amount Limit",  null, null,  "required|numeric") );
        add( new SettingCache(category(0), "max_trans_vol", "Daily Transaction Volume Limit",  null, null,  "required|numeric") );
    }};


    public static List<SettingCache> getSettings(){
        return SETTINGS;
    }

    public static Map<String, SettingCache> getSettingMap(){
        Map<String, SettingCache> settings = new HashMap<>();
        SETTINGS.forEach((setting)-> settings.put(setting.getKey(), setting));
        return settings;
    }

    private static IdName category(int position){
        return SETTING_CATEGORIES.get(position);
    }
}