package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "vendor_accounts", uniqueConstraints = @UniqueConstraint(columnNames = {"account_provider_id", "accountNumber"}))
public class VendorAccount extends BaseModel {
    @NotNull
    @Column(length = 20)
    @JsonProperty("account_number")
    private String accountNumber;

    @NotNull
    @Column(length = 100)
    @JsonProperty("account_name")
    private String accountName;

    @NotNull
    private EnabledStatus enabled = EnabledStatus.ENABLED;

    @NotNull
    @ManyToOne
    private AccountProvider accountProvider;

    @NotNull
    @OneToOne
    private Vendor vendor;
}
