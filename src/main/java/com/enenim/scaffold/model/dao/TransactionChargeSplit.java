package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "transaction_charge_splits", uniqueConstraints = @UniqueConstraint(columnNames = {"transaction_id", "benefactorId", "benefactorType"}))
public class TransactionChargeSplit extends BaseModel {

    @NotNull
    @ManyToOne
    private Transaction transaction;

    @NotNull
    @JsonProperty("benefactor_id")
    private Long benefactorId;

    @NotNull
    @Column(length = 30)
    @JsonProperty("benefactor_type")
    private String benefactorType;

    @ManyToOne
    private Settlement settlement;

    @Column(length = 20)
    private String reserved;

    @NotNull
    @ManyToOne
    private AccountProvider accountProvider;

    @NotNull
    @Column(length = 20)
    @JsonProperty("account_number")
    private String accountNumber;

    @NotNull
    @Column(length = 100)
    @JsonProperty("account_name")
    private String accountName;

    @NotNull
    private Double amount;
}

