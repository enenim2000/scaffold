package com.enenim.scaffold.util;

import com.enenim.scaffold.shared.ServicePayload;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VendorCategory {
    private String key;
    private String desc;
    private ServicePayload payload;

    public VendorCategory(String desc, String payload){
        this.desc = desc;
        this.payload = JsonConverter.getObject(payload, ServicePayload.class);
    }
}