package com.enenim.scaffold.util.setting;

import lombok.Data;

import java.util.HashMap;

@Data
public class ConsumerSettingMapCategory {
    private String key;
    private String description;
    private HashMap<String, ConsumerSystemSetting> settings = new HashMap<>();

    public ConsumerSettingMapCategory(){}

    public ConsumerSettingMapCategory(String key, String description){
        this.key = key;
        this.description = description;
    }
}
