package com.enenim.scaffold.util.setting;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VendorSettingListCategory {
    private String key;
    private String description;
    List<SystemSetting> settings = new ArrayList<>();

    public VendorSettingListCategory(){}

    public VendorSettingListCategory(String key, String description){
        this.key = key;
        this.description = description;
    }
}
