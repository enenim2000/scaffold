package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.model.dao.Vendor;
import com.enenim.scaffold.util.ObjectMapperUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@Embeddable
public class VendorProfileRequest extends RequestBody<Vendor>{

    @NotBlank(message = "@{vendor.name.required}")
    private String name;

    @NotBlank(message = "@{vendor.trading_name.required}")
    @JsonProperty("trading_name")
    @SerializedName("trading_name")
    private String tradingName;

    @NotBlank(message = "@{vendor.address.required}")
    private String address;

    @JsonProperty("phone_number")
    @SerializedName("phone_number")
    @NotBlank(message = "@{phone_number.required}")
    private String phoneNumber;

    @JsonProperty("youtube_url")
    @SerializedName("youtube_url")
    private String youtubeUrl;

    @JsonProperty("facebook_url")
    @SerializedName("facebook_url")
    private String facebookUrl;

    @JsonProperty("twitter_url")
    @SerializedName("twitter_url")
    private String twitterUrl;

    @JsonProperty("logo_url")
    @SerializedName("logo_url")
    private String logoUrl;

    @Valid
    private ContactRequest contact;

    @Override
    public Vendor buildModel() {
        return ObjectMapperUtil.map(this, Vendor.class);
    }

    @Override
    public Vendor buildModel(Vendor vendor) {
        return ObjectMapperUtil.map(this, vendor);
    }
}