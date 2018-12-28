package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.model.dao.Biller;
import com.enenim.scaffold.util.ObjectMapperUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class BillerSignUpVerifyRequest extends RequestBody<Biller>{

    @NotBlank(message = "@{biller.password.required}")
    private String password;

    @Override
    public Biller buildModel() {
        return ObjectMapperUtil.map(this, Biller.class);
    }

    @Override
    public Biller buildModel(Biller biller) {
        return ObjectMapperUtil.map(this, biller);
    }
}
