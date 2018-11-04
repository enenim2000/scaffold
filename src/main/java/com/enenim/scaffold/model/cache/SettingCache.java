package com.enenim.scaffold.model.cache;

import com.enenim.scaffold.shared.IdName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class SettingCache {
    private Integer id;
    private IdName category;
    private String key = "";
    private String desc;

    @JsonProperty("default_val")
    private String defaultVal;

    private Map<String, String> options;
    private String value;
    private String revision;
    private String validation;

    public SettingCache(IdName category, String settingKey, String desc, String defaultVal, Map<String, String> options, String validation){
        this.category = category;
        this.key = settingKey;
        this.desc = desc;
        this.defaultVal = defaultVal;
        this.options = options;
        this.validation = validation;
    }
}
