package com.enenim.scaffold.util.setting;

import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.util.JsonConverter;
import com.enenim.scaffold.util.RequestUtil;
import org.springframework.util.StringUtils;

import java.util.HashMap;

public class VendorSettingConfigUtil {

    private static final String VENDOR_ONLY = "[ " + RoleConstant.VENDOR + "]";
    private static final String STAFF_VENDOR_ONLY = "[ " + RoleConstant.STAFF + "," + RoleConstant.VENDOR + "]";
    private static final String STAFF_ONLY = "[ " + RoleConstant.STAFF + "]";

    private static HashMap<String, VendorSystemSetting> MEMORY_SETTINGS_BY_KEY = new HashMap<>();
    private static HashMap<String, VendorSettingMapCategory> MEMORY_SETTINGS_BY_CATEGORY = new HashMap<>();

    private static final HashMap<String, String> TRANSACTION_CONFIG = new HashMap<String, String>(){{
        put("transaction_amount", "{\"type\":\"number\",\"label\": \"Daily Transaction Amount Limit\", \"placeholder\": \"Enter daily transaction amount limit\", \"value\":\"250000\", \"maxlength\":\"1\", \"minlength\":\"10000000\", \"options\":null, \"user_types\":" + STAFF_VENDOR_ONLY + "}");
        put("transaction_volume", "{\"type\":\"number\",\"label\": \"Daily Transaction Volume Limit\", \"placeholder\": \"Enter daily transaction volume limit\", \"value\":\"5\", \"maxlength\":\"1\", \"minlength\":\"100\", \"options\":null, \"user_types\":" + STAFF_VENDOR_ONLY + "}");
    }};

    private static final HashMap<String, String> GENERAL_CONFIG = new HashMap<String, String>(){{
        put("support_notification", "{\"type\":\"text\",\"label\": \"Support Notification\", \"placeholder\": \"Enter notification emails\", \"value\":\"enenim.asukwo@curexzone.com,luther.emmanuel@curexzone.com\", \"maxlength\":\"\", \"minlength\":\"\", \"options\":null, \"user_types\":" + STAFF_VENDOR_ONLY + "}");
    }};

    private static final HashMap<String, String> NOTIFICATION_CONFIG = new HashMap<String, String>(){{
        put("new_releases", "{\"type\":\"checkbox\",\"label\": \"Notify me for new release\", \"placeholder\": \"Notify me on release news/updates\", \"value\":\"yes\", \"maxlength\":\"\", \"minlength\":\"\", \"options\":[{ \"key\": \"yes\", \"value\": \"Yes\" }, { \"key\": \"no\", \"value\": \"No\" } ], \"user_types\":" + VENDOR_ONLY + "}");
    }};

    private static String[] CATEGORY_KEYS = {"transaction_config", "general_config", "notification_config"};

    private static final HashMap<String, VendorSettingListCategory> CATEGORY_DESCRIPTION = new HashMap<String, VendorSettingListCategory>(){{
        put(CATEGORY_KEYS[0], new VendorSettingListCategory(CATEGORY_KEYS[0], "Transaction"));
        put(CATEGORY_KEYS[1], new VendorSettingListCategory(CATEGORY_KEYS[1] ,"General"));
        put(CATEGORY_KEYS[2], new VendorSettingListCategory(CATEGORY_KEYS[2] ,"Notification"));
    }};

    private static final HashMap<String, HashMap<String, String>> SETTING_CONFIG = new HashMap<String, HashMap<String, String>>(){{
        put(CATEGORY_KEYS[0], TRANSACTION_CONFIG);
        put(CATEGORY_KEYS[1], GENERAL_CONFIG);
        put(CATEGORY_KEYS[2], NOTIFICATION_CONFIG);
    }};
    
    private static HashMap<String, VendorSettingMapCategory> getVendorSettingAsMap(){

        HashMap<String, VendorSettingMapCategory> settingCategories = new HashMap<>();

        for(HashMap.Entry<String, HashMap<String, String>> categoryEntry : SETTING_CONFIG.entrySet()){

            VendorSettingListCategory listCategory = CATEGORY_DESCRIPTION.get(categoryEntry.getKey());
            VendorSettingMapCategory category = new VendorSettingMapCategory(listCategory.getKey(), listCategory.getDescription());

            HashMap<String, VendorSystemSetting> vendorSystemSettings = new HashMap<>();

            for(HashMap.Entry<String, String> settingEntry : categoryEntry.getValue().entrySet()){
                VendorSystemSetting vendorSystemSetting = new VendorSystemSetting();
                vendorSystemSetting.setSettingKey(settingEntry.getKey());
                vendorSystemSetting.setCategoryKey(categoryEntry.getKey());
                vendorSystemSetting.setDetail(JsonConverter.getObject(settingEntry.getValue(), VendorSystemSettingDetail.class));
                vendorSystemSettings.put(settingEntry.getKey(), vendorSystemSetting);
            }

            category.setSettings(vendorSystemSettings);
            settingCategories.put(category.getKey(), category);
        }

        return settingCategories;
    }

    public static void loadVendorSystemSettings(){
        HashMap<String, VendorSettingMapCategory> settingMapCategories = getVendorSettingAsMap();
        for(HashMap.Entry<String, VendorSettingMapCategory> categoryEntry : settingMapCategories.entrySet()){
            for(HashMap.Entry<String, VendorSystemSetting> settingEntry : categoryEntry.getValue().getSettings().entrySet()){
                MEMORY_SETTINGS_BY_KEY.put(settingEntry.getKey(), settingEntry.getValue());
                if(StringUtils.isEmpty(MEMORY_SETTINGS_BY_CATEGORY.get(categoryEntry.getKey()))){
                    MEMORY_SETTINGS_BY_CATEGORY.put(categoryEntry.getKey(), settingMapCategories.get(categoryEntry.getKey()));
                }
                MEMORY_SETTINGS_BY_CATEGORY.get(categoryEntry.getKey()).getSettings().put(settingEntry.getKey(), settingEntry.getValue());
            }
        }
    }

    private static HashMap<String, VendorSettingMapCategory> getFilteredVendorSettings(){
        HashMap<String, VendorSettingMapCategory> filteredVendorSettings = new HashMap<>();
        for(HashMap.Entry<String, VendorSettingMapCategory> categoryEntry : MEMORY_SETTINGS_BY_CATEGORY.entrySet()){
            for(HashMap.Entry<String, VendorSystemSetting> settingEntry : categoryEntry.getValue().getSettings().entrySet()){

                if(settingEntry.getValue().getDetail().getUserTypes().contains(RequestUtil.getLogin().getUserType())){
                    if(StringUtils.isEmpty(filteredVendorSettings.get(categoryEntry.getKey()))){
                        filteredVendorSettings.put(categoryEntry.getKey(), MEMORY_SETTINGS_BY_CATEGORY.get(categoryEntry.getKey()));
                    }
                    filteredVendorSettings.get(categoryEntry.getKey()).getSettings().put(settingEntry.getKey(), settingEntry.getValue());
                }
            }
        }
        return filteredVendorSettings;
    }

    public static HashMap<String, VendorSettingMapCategory> getMemoryVendorSettings() {
        return getFilteredVendorSettings();
    }
}