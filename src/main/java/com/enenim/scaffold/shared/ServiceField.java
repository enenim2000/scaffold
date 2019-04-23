package com.enenim.scaffold.shared;

import lombok.Data;

import java.util.List;

@Data
public class ServiceField {

    private String type;
    private String name;
    private String value;
    private List<KeyValue> options;
    private String label;
    private String placeholder;

}
