package com.enenim.scaffold.util;

import lombok.Data;

import java.util.Map;

@Data
public class SystemSetting {
    private String key;
    Map<String, Object> detail;
}