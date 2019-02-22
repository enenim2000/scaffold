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
@Table(name = "payment_channels")
public class PaymentChannel extends BaseModel {

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    @Column(unique = true)
    private String code;

    @Column(length = 7)
    @JsonProperty("txn_prefix")
    private String txnPrefix;

    @NotNull
    @Column(length = 20)
    @JsonProperty("notification_medium")
    private String notificationMedium;

    @NotNull
    private EnabledStatus enabled = EnabledStatus.ENABLED;

    @JsonBackReference
    @ManyToMany(mappedBy = "paymentChannels")
    private Set<Vendor> vendors = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "paymentChannel", fetch = FetchType.LAZY)
    private Set<Transaction> transactions = new HashSet<>();
}