package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.model.dao.Vendor;
import com.enenim.scaffold.util.ObjectMapperUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class VendorNewPasswordRequest extends RequestBody<Vendor>{

    @NotBlank(message = "@{vendor.password.required}")
    private String password;

    @NotBlank(message = "@{vendor.confirm_password.required}")
    @JsonProperty("confirm_password")
    private String confirmPassword;

    @Override
    public Vendor buildModel() {
        return ObjectMapperUtil.map(this, Vendor.class);
    }

    @Override
    public Vendor buildModel(Vendor vendor) {
        return ObjectMapperUtil.map(this, vendor);
    }
}
