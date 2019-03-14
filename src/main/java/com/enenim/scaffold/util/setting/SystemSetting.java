package com.enenim.scaffold.util.setting;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class SystemSetting {

    @JsonProperty("category_key")
    @SerializedName("category_key")
    private String categoryKey;

    @JsonProperty("setting_key")
    @SerializedName("setting_key")
    private String settingKey;

    private SystemSettingDetail detail;
}