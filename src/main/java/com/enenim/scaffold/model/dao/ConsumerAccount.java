package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "consumer_accounts")
public class ConsumerAccount extends BaseModel {
    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("account_name")
    private String accountName;

    @JsonProperty("email")
    @Column(length = 70)
    private String email;

    @JsonProperty("phone")
    @Column(length = 15)
    private String phone;

    @JsonProperty("branch_code")
    @Column(length = 6)
    private String branchCode;

    private EnabledStatus enabled = EnabledStatus.ENABLED;

    @ManyToOne
    private Consumer consumer;

    @ManyToOne
    private AccountProvider accountProvider;
}
