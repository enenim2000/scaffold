package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.model.dao.Setting;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class SettingRequest extends RequestBody<Setting> {

    @NotBlank
    @JsonProperty("category_key")
    private String categoryKey;

    @NotBlank
    @JsonProperty("setting_key")
    private String settingKey;

    @NotBlank
    private String value;

    @Override
    public Setting buildModel() {
        return null;
    }

    @Override
    public Setting buildModel(Setting model) {
        return null;
    }
}