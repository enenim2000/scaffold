package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "payment_methods")
public class PaymentMethod extends BaseModel {

    @NotNull
    @Column(unique = true, length = 40)
    private String slug;

    @NotNull
    @Column(unique = true, length = 40)
    private String name;

    @NotNull
    @Column(unique = true, length = 4)
    private String code;

    @Column(length = 3)
    @JsonProperty("txn_prefix")
    private String txnPrefix;

    @NotNull
    @Column(length = 20)
    @JsonProperty("holding_account")
    private String holdingAccount;

    @NotNull
    private EnabledStatus enabled = EnabledStatus.ENABLED;

    @JsonBackReference
    @ManyToMany(mappedBy = "paymentMethods", fetch = FetchType.LAZY)
    private Set<Vendor> vendors = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "paymentMethod", fetch = FetchType.LAZY)
    private Set<Transaction> transactions = new HashSet<>();
}
