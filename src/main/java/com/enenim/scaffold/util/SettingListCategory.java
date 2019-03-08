package com.enenim.scaffold.util;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SettingListCategory {
    private String key;
    private String description;
    List<SystemSetting> settings = new ArrayList<>();

    public SettingListCategory(){}

    public SettingListCategory(String key, String description){

    }
}
