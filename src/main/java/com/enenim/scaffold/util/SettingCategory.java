package com.enenim.scaffold.util;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SettingCategory {
    private String key;
    private String description;
    List<SystemSetting> settings = new ArrayList<>();

    public SettingCategory(){}

    public SettingCategory(String key, String description){

    }
}
