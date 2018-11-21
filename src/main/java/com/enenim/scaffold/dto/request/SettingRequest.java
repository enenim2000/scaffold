package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.model.dao.Setting;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class SettingRequest extends RequestBody<Setting> {

    @NotBlank
    private String key;

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