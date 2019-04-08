package com.enenim.scaffold.util.setting;

import lombok.Data;

import java.util.HashMap;

@Data
public class VendorSettingMapCategory {
    private String key;
    private String description;
    private HashMap<String, VendorSystemSetting> settings = new HashMap<>();

    public VendorSettingMapCategory(){}

    public VendorSettingMapCategory(String key, String description){
        this.key = key;
        this.description = description;
    }
}
