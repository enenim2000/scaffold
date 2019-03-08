package com.enenim.scaffold.util;

import com.enenim.scaffold.shared.KeyValue;
import lombok.Data;

import java.util.List;

@Data
public class SystemSettingDetail {
    private String value;
    private String type;
    private String label;
    private String placeholder;
    private String maxlength;
    private String minlength;
    private List<KeyValue> options;
}