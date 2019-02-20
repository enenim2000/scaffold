package com.enenim.scaffold.dto.response;

import com.enenim.scaffold.enums.VendorType;
import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.enums.VerifyStatus;
import com.enenim.scaffold.model.BaseModel;
import com.enenim.scaffold.model.dao.VendorAccount;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class VendorResponse extends BaseModel {

    private String name;

    private String address;

    private String email;

    @JsonProperty("phone_number")
    @SerializedName("phone_number")
    private String phoneNumber;

    private String code;

    @NotNull
    @Column(unique = true, length = 100)
    @JsonProperty("trading_name")
    @SerializedName("trading_name")
    private String tradingName;

    private String slug;

    @JsonProperty("logo_path")
    @SerializedName("logo_path")
    private String logoPath;

    @NotNull
    private VendorType type = VendorType.REGULAR;

    @NotNull
    private VerifyStatus verified = VerifyStatus.NOT_VERIFIED;

    @NotNull
    private EnabledStatus enabled = EnabledStatus.ENABLED;

    @JsonProperty("vendor_account")
    @SerializedName("vendor_account")
    private VendorAccount vendorAccount;
}