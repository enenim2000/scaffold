package com.enenim.scaffold.util;

import lombok.Data;

import java.util.HashMap;

@Data
public class SettingMapCategory {
    private String key;
    private String description;
    private HashMap<String, SystemSetting> settings = new HashMap<>();

    public SettingMapCategory(){}

    public SettingMapCategory(String key, String description){
        this.key = key;
        this.description = description;
    }
}
