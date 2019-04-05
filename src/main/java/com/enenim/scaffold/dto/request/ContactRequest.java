package com.enenim.scaffold.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
public class ContactRequest{

    @Length(min = 2, max = 2)
    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("state")
    private String state;

    @JsonProperty("province")
    private String province;

    @JsonProperty("zip_code")
    private String zipCode;

    @JsonProperty("city")
    private String city;

    @JsonProperty("address_line1")
    private String addressLine1;

    @JsonProperty("address_line2")
    private String addressLine2;
}