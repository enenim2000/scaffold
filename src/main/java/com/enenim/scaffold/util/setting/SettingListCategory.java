package com.enenim.scaffold.util.setting;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SettingListCategory {

    @JsonProperty("category_key")
    private String categoryKey;

    private String description;

    List<SystemSetting> settings = new ArrayList<>();

    public SettingListCategory(){}

    public SettingListCategory(String categoryKey, String description){
        this.categoryKey = categoryKey;
        this.description = description;
    }
}
