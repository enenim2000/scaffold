package com.enenim.scaffold.shared;

import lombok.Data;

import java.util.List;

@Data
public class ServiceFormPage {

    private String title;
    private List<ServiceField> fields;

}
