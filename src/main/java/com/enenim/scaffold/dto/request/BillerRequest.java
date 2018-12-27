package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.model.dao.Biller;
import com.enenim.scaffold.util.ObjectMapperUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class BillerRequest extends RequestBody<Biller>{

    @NotBlank(message = "@{biller.name.required}")
    private String name;

    @NotBlank(message = "@{biller.trading_name.required}")
    @JsonProperty("trading_name")
    @SerializedName("trading_name")
    private String tradingName;

    @NotBlank(message = "@{biller.address.required}")
    private String address;

    @JsonProperty("phone_number")
    @SerializedName("phone_number")
    @NotBlank(message = "@{biller.phone_number.required}")
    private String phoneNumber;

    @NotBlank(message = "@{biller.email.required}")
    @Email(message = "@{biller.email.pattern}")
    private String email;

    @Override
    public Biller buildModel() {
        return ObjectMapperUtil.map(this, Biller.class);
    }

    @Override
    public Biller buildModel(Biller biller) {
        return ObjectMapperUtil.map(this, biller);
    }
}