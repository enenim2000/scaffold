package com.enenim.scaffold.util.setting;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ConsumerSettingListCategory {
    private String key;
    private String description;
    List<SystemSetting> settings = new ArrayList<>();

    public ConsumerSettingListCategory(){}

    public ConsumerSettingListCategory(String key, String description){
        this.key = key;
        this.description = description;
    }
}
