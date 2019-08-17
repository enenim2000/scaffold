package com.enenim.scaffold.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class SettingResetRequest {

    @NotBlank
    @JsonProperty("category_key")
    private String categoryKey;

    @NotBlank
    @JsonProperty("setting_key")
    private String settingKey;
}