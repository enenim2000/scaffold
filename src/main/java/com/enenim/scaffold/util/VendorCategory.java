package com.enenim.scaffold.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VendorCategory {
    private String key;
    private String desc;
    private String payload;

    public VendorCategory(String desc, String payload){
        this.desc = desc;
        this.payload = payload;
    }
}