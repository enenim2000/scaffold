package com.enenim.scaffold.model.cache;

import com.enenim.scaffold.shared.IdName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.util.Map;

@ToString
@Getter
@Setter
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

    public SettingCache(IdName category, String key, String desc, String defaultVal, Map<String, String> options, String validation){
        this.category = category;
        this.key = key;
        this.desc = desc;
        this.defaultVal = defaultVal;
        this.options = options;
        this.validation = validation;
    }

    public String getValue(){
        return StringUtils.isEmpty(this.value) ? getDefaultVal() : this.value;
    }
}
