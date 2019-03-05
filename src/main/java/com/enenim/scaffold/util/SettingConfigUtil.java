package com.enenim.scaffold.util;

import java.util.HashMap;

public class SettingConfigUtil {

    private static final HashMap<String, String> CONTACT_INFO_CONFIG = new HashMap<String, String>(){{
        put("application_name", "");
        put("contact_address", "");
        put("contact_email", "");
    }};

    private static final HashMap<String, String> ACTION_CENTER_CONFIG = new HashMap<String, String>(){{
        put("support_notification", "");
    }};

    private static final HashMap<String, String> SECURITY_CENTER_CONFIG = new HashMap<String, String>(){{
        put("min_pwd_length", "");
        put("authorize_direct_forwarding", "");
        put("session_timeout", "");
    }};

    private static final HashMap<String, String> LANGUAGE_SUPPORT_CONFIG = new HashMap<String, String>(){{
        put("default_system_lang","");
        put("enable_lang_preference","");
    }};

    private static final HashMap<String, String> SETTLEMENT_PREFERENCE_CONFIG = new HashMap<String, String>(){{
        put("min_settlement_duration", "");
    }};

    private static final HashMap<String, HashMap<String, String>> SETTING_CONFIG = new HashMap<String, HashMap<String, String>>(){{
        put("contact_info_config", CONTACT_INFO_CONFIG);
        put("action_center_config", ACTION_CENTER_CONFIG);
        put("security_center_config", SECURITY_CENTER_CONFIG);
        put("language_support_config", LANGUAGE_SUPPORT_CONFIG);
        put("settlement_preference_config", SETTLEMENT_PREFERENCE_CONFIG);
    }};

    public HashMap<String, HashMap<String, String>> getSettingCategoryMap(){
        return SETTING_CONFIG;
    }

    public HashMap<String, String> getCategoryMap(String categoryKey){
        return SETTING_CONFIG.get(categoryKey);
    }

    public String getSetting(String categoryKey, String settingKey){
        return SETTING_CONFIG.get(categoryKey).get(settingKey);
    }
}
