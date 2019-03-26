package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "account_providers")
public class AccountProvider extends BaseModel {

    @NotNull
    @Column(unique=true, length = 50)
    private String name;

    @NotNull
    @Column(unique=true, length = 10)
    private String code;

    @NotNull
    private EnabledStatus enabled = EnabledStatus.ENABLED;

    public AccountProvider(){}

    public AccountProvider(Long id){
        this.id = id;
    }

    @JsonBackReference
    @OneToMany(mappedBy = "accountProvider", fetch = FetchType.LAZY)
    @JsonProperty("vendor_accounts")
    @ApiModelProperty(required = true, hidden = true)
    private Set<VendorAccount> vendorAccounts = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "accountProvider", fetch = FetchType.LAZY)
    @JsonProperty("consumer_accounts")
    @ApiModelProperty(required = true, hidden = true)
    private Set<ConsumerAccount> consumerAccounts = new HashSet<>();
}