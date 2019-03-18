package com.enenim.scaffold.util.setting;

import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.util.JsonConverter;

import java.util.HashMap;

public class ConsumerSettingConfigUtil {

    private static final String CONSUMER_ONLY = "[ " + RoleConstant.CONSUMER + "]";
    private static final String STAFF_CONSUMER_ONLY = "[ " + RoleConstant.STAFF + "," + RoleConstant.CONSUMER + "]";
    private static final String STAFF_ONLY = "[ " + RoleConstant.STAFF + "]";

    private static final HashMap<String, String> TRANSACTION_CONFIG = new HashMap<String, String>(){{
        put("transaction_amount", "{\"type\":\"number\",\"label\": \"Daily Transaction Amount Limit\", \"placeholder\": \"Enter daily transaction amount limit\", \"value\":\"250000\", \"maxlength\":\"1\", \"minlength\":\"10000000\", \"options\":null, \"user_types\":" + STAFF_CONSUMER_ONLY + "}");
        put("transaction_volume", "{\"type\":\"number\",\"label\": \"Daily Transaction Volume Limit\", \"placeholder\": \"Enter daily transaction volume limit\", \"value\":\"5\", \"maxlength\":\"1\", \"minlength\":\"100\", \"options\":null, \"user_types\":" + STAFF_CONSUMER_ONLY + "}");
    }};

    private static final HashMap<String, String> GENERAL_CONFIG = new HashMap<String, String>(){{
        put("support_notification", "{\"type\":\"text\",\"label\": \"Support Notification\", \"placeholder\": \"Enter notification emails\", \"value\":\"enenim.asukwo@curexzone.com,luther.emmanuel@curexzone.com\", \"maxlength\":\"\", \"minlength\":\"\", \"options\":null, \"user_types\":" + STAFF_CONSUMER_ONLY + "}");
    }};

    private static final HashMap<String, String> NOTIFICATION_CONFIG = new HashMap<String, String>(){{
        put("new_releases", "{\"type\":\"checkbox\",\"label\": \"Notify me for new release\", \"placeholder\": \"Notify me on release news/updates\", \"value\":\"yes\", \"maxlength\":\"\", \"minlength\":\"\", \"options\":[{ \"key\": \"yes\", \"value\": \"Yes\" }, { \"key\": \"no\", \"value\": \"No\" } ], \"user_types\":" + CONSUMER_ONLY + "}");
    }};

    private static String[] CATEGORY_KEYS = {"transaction_config", "general_config", "notification_config"};

    private static final HashMap<String, ConsumerSettingListCategory> CATEGORY_DESCRIPTION = new HashMap<String, ConsumerSettingListCategory>(){{
        put(CATEGORY_KEYS[0], new ConsumerSettingListCategory(CATEGORY_KEYS[0], "Transaction"));
        put(CATEGORY_KEYS[1], new ConsumerSettingListCategory(CATEGORY_KEYS[1] ,"General"));
        put(CATEGORY_KEYS[2], new ConsumerSettingListCategory(CATEGORY_KEYS[2] ,"Notification"));
    }};

    private static final HashMap<String, HashMap<String, String>> SETTING_CONFIG = new HashMap<String, HashMap<String, String>>(){{
        put(CATEGORY_KEYS[0], TRANSACTION_CONFIG);
        put(CATEGORY_KEYS[1], GENERAL_CONFIG);
        put(CATEGORY_KEYS[2], NOTIFICATION_CONFIG);
    }};
    
    public static HashMap<String, ConsumerSettingMapCategory> getSettingAsMap(){

        HashMap<String, ConsumerSettingMapCategory> settingCategories = new HashMap<>();

        for(HashMap.Entry<String, HashMap<String, String>> categoryEntry : SETTING_CONFIG.entrySet()){

            ConsumerSettingListCategory listCategory = CATEGORY_DESCRIPTION.get(categoryEntry.getKey());
            ConsumerSettingMapCategory category = new ConsumerSettingMapCategory(listCategory.getKey(), listCategory.getDescription());

            HashMap<String, ConsumerSystemSetting> consumerSystemSettings = new HashMap<>();

            for(HashMap.Entry<String, String> settingEntry : categoryEntry.getValue().entrySet()){
                ConsumerSystemSetting consumerSystemSetting = new ConsumerSystemSetting();
                consumerSystemSetting.setSettingKey(settingEntry.getKey());
                consumerSystemSetting.setCategoryKey(categoryEntry.getKey());
                consumerSystemSetting.setDetail(JsonConverter.getObject(settingEntry.getValue(), ConsumerSettingDetail.class));
                consumerSystemSettings.put(settingEntry.getKey(), consumerSystemSetting);
            }

            category.setSettings(consumerSystemSettings);

            settingCategories.put(category.getKey(), category);
        }

        return settingCategories;
    }
}
