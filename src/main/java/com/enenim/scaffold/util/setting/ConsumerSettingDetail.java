package com.enenim.scaffold.util.setting;

import com.enenim.scaffold.shared.KeyValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class ConsumerSettingDetail {
    private String value;
    private String type;
    private String label;
    private String placeholder;
    private String maxlength;
    private String minlength;
    private List<KeyValue> options;

    @JsonProperty("user_types")
    @SerializedName("user_types")
    private List<KeyValue> userTypes;
}