package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.model.dao.Biller;
import com.enenim.scaffold.util.ObjectMapperUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class BillerChangePasswordRequest extends RequestBody<Biller>{

    @NotBlank(message = "@{biller.password.required}")
    private String password;

    @NotBlank(message = "@{biller.confirm_password.required}")
    @JsonProperty("confirm_password")
    private String confirmPassword;

    @Override
    public Biller buildModel() {
        return ObjectMapperUtil.map(this, Biller.class);
    }

    @Override
    public Biller buildModel(Biller biller) {
        return ObjectMapperUtil.map(this, biller);
    }
}
