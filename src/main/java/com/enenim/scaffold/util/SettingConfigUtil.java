package com.enenim.scaffold.util;

import com.enenim.scaffold.model.dao.Setting;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingConfigUtil {

    private static HashMap<String, Setting> DATABASE_SETTINGS = new HashMap<>();
    private static HashMap<String, SystemSetting> MEMORY_SETTINGS_BY_KEY = new HashMap<>();
    private static HashMap<String, SettingMapCategory> MEMORY_SETTINGS_BY_CATEGORY = new HashMap<>();

    private static final HashMap<String, String> CONTACT_INFO_CONFIG = new HashMap<String, String>(){{
        put("application_name", "{\"type\":\"text\",\"label\": \"Application name\", \"placeholder\": \"Enter application name\", \"value\":\"\", \"maxlength\":\"\", \"minlength\":\"\", \"options\":null}");
    }};

    private static final HashMap<String, String> ACTION_CENTER_CONFIG = new HashMap<String, String>(){{
        put("support_notification", "{\"type\":\"text\",\"label\": \"Support Notification\", \"placeholder\": \"Enter notification emails\", \"value\":\"enenim.asukwo@curexzone.com,luther.emmanuel@curexzone.com\", \"maxlength\":\"\", \"minlength\":\"\", \"options\":null}");
    }};

    private static final HashMap<String, String> SECURITY_CENTER_CONFIG = new HashMap<String, String>(){{
        put("min_pwd_length", "{\"type\":\"text\",\"label\": \"Minimum password length\", \"placeholder\": \"Enter minimum password length\", \"value\":\"eight\", \"maxlength\":\"\", \"minlength\":\"\", \"options\": [ { \"settingKey\": \"eight\", \"value\": \"8\" }, { \"settingKey\": \"ten\", \"value\": \"10\" }, { \"settingKey\": \"fifteen\", \"value\": \"15\" } ]}");
        put("enable_multiple_login", "{\"type\":\"select\",\"label\": \"Allow multiple login sessions\", \"placeholder\": \"Choose an option\", \"value\":\"yes\", \"maxlength\":\"\", \"minlength\":\"\", \"options\":[{ \"settingKey\": \"yes\", \"value\": \"Yes\" }, { \"settingKey\": \"no\", \"value\": \"No\" } ]}");
    }};

    private static final HashMap<String, String> LANGUAGE_SUPPORT_CONFIG = new HashMap<String, String>(){{
        put("default_system_lang","{\"type\":\"select\",\"label\": \"Default language\", \"placeholder\": \"Choose default language\", \"value\":\"en\", \"maxlength\":\"\", \"minlength\":\"\", \"options\":[{ \"settingKey\": \"en\", \"value\": \"English\" }, { \"settingKey\": \"fr\", \"value\": \"French\" }, { \"settingKey\": \"es\", \"value\": \"Spanish\" } ]}");
        put("enable_lang_preference","{\"type\":\"select\",\"label\": \"Enable language preference\", \"placeholder\": \"Language preference\", \"value\":\"yes\", \"maxlength\":\"\", \"minlength\":\"\", \"options\":[{ \"settingKey\": \"yes\", \"value\": \"Yes\" }, { \"settingKey\": \"no\", \"value\": \"No\" } ]}");
    }};

    private static final HashMap<String, String> SETTLEMENT_PREFERENCE_CONFIG = new HashMap<String, String>(){{
        put("min_settlement_duration", "{\"type\":\"select\",\"label\": \"Minimum Duration in hour(s) before settlement\", \"placeholder\": \"Select preferred settlement duration\", \"value\":\"yes\", \"maxlength\":\"2\", \"minlength\":\"1\", \"options\":[{ \"settingKey\": \"1\", \"value\": \"1 hour\" }, { \"settingKey\": \"24\", \"value\": \"24 hours\" }, { \"settingKey\": \"48\", \"value\": \"48 hours\" } ]}");
    }};

    private static String[] CATEGORY_KEYS = {"contact_info_config", "action_center_config", "security_center_config", "language_support_config", "settlement_preference_config"};

    private static final HashMap<String, SettingListCategory> CATEGORY_DESCRIPTION = new HashMap<String, SettingListCategory>(){{
        put(CATEGORY_KEYS[0], new SettingListCategory(CATEGORY_KEYS[0], "Contact Information"));
        put(CATEGORY_KEYS[1], new SettingListCategory(CATEGORY_KEYS[1] ,"Action Center"));
        put(CATEGORY_KEYS[2], new SettingListCategory(CATEGORY_KEYS[2] ,"Security Center"));
        put(CATEGORY_KEYS[3], new SettingListCategory(CATEGORY_KEYS[3] ,"Language Support"));
        put(CATEGORY_KEYS[4], new SettingListCategory(CATEGORY_KEYS[4] ,"Settlement Preference"));
    }};

    private static final HashMap<String, HashMap<String, String>> SETTING_CONFIG = new HashMap<String, HashMap<String, String>>(){{
        put(CATEGORY_KEYS[0], CONTACT_INFO_CONFIG);
        put(CATEGORY_KEYS[1], ACTION_CENTER_CONFIG);
        put(CATEGORY_KEYS[2], SECURITY_CENTER_CONFIG);
        put(CATEGORY_KEYS[3], LANGUAGE_SUPPORT_CONFIG);
        put(CATEGORY_KEYS[4], SETTLEMENT_PREFERENCE_CONFIG);
    }};

    public static List<SettingListCategory> getSettingAsList(){

        List<SettingListCategory> settingCategories = new ArrayList<>();

        for(HashMap.Entry<String, HashMap<String, String>> categoryEntry : SETTING_CONFIG.entrySet()){

            SettingListCategory category = CATEGORY_DESCRIPTION.get(categoryEntry.getKey());

            List<SystemSetting> systemSettings = new ArrayList<>();

            for(HashMap.Entry<String, String> settingEntry : categoryEntry.getValue().entrySet()){
                SystemSetting systemSetting = new SystemSetting();
                systemSetting.setSettingKey(settingEntry.getKey());
                systemSetting.setCategoryKey(categoryEntry.getKey());
                systemSetting.setDetail(JsonConverter.getObject(settingEntry.getValue(), SystemSettingDetail.class));
                systemSettings.add(systemSetting);
            }

            category.setSettings(systemSettings);

            settingCategories.add(category);
        }

        return settingCategories;
    }

    private static HashMap<String, SettingMapCategory> getSettingAsMap(){

        HashMap<String, SettingMapCategory> settingCategories = new HashMap<>();

        for(HashMap.Entry<String, HashMap<String, String>> categoryEntry : SETTING_CONFIG.entrySet()){

            SettingListCategory listCategory = CATEGORY_DESCRIPTION.get(categoryEntry.getKey());
            SettingMapCategory category = new SettingMapCategory(listCategory.getKey(), listCategory.getDescription());

            HashMap<String, SystemSetting> systemSettings = new HashMap<>();

            for(HashMap.Entry<String, String> settingEntry : categoryEntry.getValue().entrySet()){
                SystemSetting systemSetting = new SystemSetting();
                systemSetting.setSettingKey(settingEntry.getKey());
                systemSetting.setCategoryKey(categoryEntry.getKey());
                systemSetting.setDetail(JsonConverter.getObject(settingEntry.getValue(), SystemSettingDetail.class));
                systemSettings.put(settingEntry.getKey(), systemSetting);
            }

            category.setSettings(systemSettings);

            settingCategories.put(category.getKey(), category);
        }

        return settingCategories;
    }

    public static void loadDatabaseSettings(List<Setting> settings){
        for (Setting setting : settings){
            if(!StringUtils.isEmpty(setting)){
                DATABASE_SETTINGS.put(setting.getSettingKey(), setting);
            }
        }
    }

    public static void updateSystemSetting(Setting setting){
        if(!StringUtils.isEmpty(setting)){
            SystemSetting systemSetting = MEMORY_SETTINGS_BY_KEY.get(setting.getSettingKey());
            systemSetting.getDetail().setValue(setting.getValue());
            DATABASE_SETTINGS.put(setting.getSettingKey(), setting);
            MEMORY_SETTINGS_BY_KEY.put(setting.getSettingKey(), systemSetting);
            MEMORY_SETTINGS_BY_CATEGORY.get(setting.getCategoryKey()).getSettings().put(setting.getSettingKey(), systemSetting);
        }
    }

    public static void loadSystemSettings(){
        HashMap<String, SettingMapCategory> settingMapCategories = getSettingAsMap();
        for(HashMap.Entry<String, SettingMapCategory> categoryEntry : settingMapCategories.entrySet()){
            for(HashMap.Entry<String, SystemSetting> settingEntry : categoryEntry.getValue().getSettings().entrySet()){
                if(DATABASE_SETTINGS.containsKey(settingEntry.getKey())){
                    Setting setting = DATABASE_SETTINGS.get(settingEntry.getKey());
                    settingEntry.getValue().getDetail().setValue( setting.getValue() );
                }
                MEMORY_SETTINGS_BY_KEY.put(settingEntry.getKey(), settingEntry.getValue());
                MEMORY_SETTINGS_BY_CATEGORY.get(categoryEntry.getKey()).getSettings().put(settingEntry.getKey(), settingEntry.getValue());
            }
        }
    }

    /**
     * This method returns all settings within a given category
     * @return category settings
     */
    public SettingMapCategory getSettingsByCategory(String key){
        return MEMORY_SETTINGS_BY_CATEGORY.get(key);
    }

    /**
     * This method returns all settings arranged per setting settingKey
     * @return settings by settingKey
     */
    public static HashMap<String, SystemSetting> getSystemSettings() {
        return MEMORY_SETTINGS_BY_KEY;
    }

    /**
     * This method returns all settings arranged per category
     * @return categorized settings
     */
    public static HashMap<String, SettingMapCategory> getCategorizedSettings() {
        return MEMORY_SETTINGS_BY_CATEGORY;
    }

    public static SystemSetting getSystemSetting(String key){
        return MEMORY_SETTINGS_BY_KEY.get(key);
    }
}
