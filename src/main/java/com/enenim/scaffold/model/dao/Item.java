package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.AmountType;
import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.enums.PayAgain;
import com.enenim.scaffold.enums.PayAgainType;
import com.enenim.scaffold.interfaces.DataTypeConstant;
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
@Table(name = "items", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"biller_id", "code"}),
        @UniqueConstraint(columnNames = {"biller_id", "name"})
})
public class Item extends BaseModel {

    @NotNull
    @Column(length = 50)
    private String name;

    @NotNull
    @Column(length = 20)
    private String code;

    @NotNull
    @JsonProperty("amount_type")
    private AmountType amountType = AmountType.VARIABLE_AMOUNT;

    @NotNull
    @JsonProperty("customer_id_label")
    private String customerIdLabel = "";

    @NotNull
    @JsonProperty("pay_again")
    private PayAgain payAgain = PayAgain.NO;

    @JsonProperty("pay_again_type")
    private PayAgainType payAgainType = PayAgainType.EMPTY;

    private Double amount = 0.00;

    @NotNull
    @Column(length = 100)
    private String description;

    @NotNull
    @Column(unique = true, length = 200)
    private String slug = "";

    @Column(columnDefinition = DataTypeConstant.TEXT)
    private String surcharge = "";

    @NotNull
    private EnabledStatus enabled = EnabledStatus.ENABLED;

    @NotNull
    @ManyToOne
    private Biller biller =  new Biller();

    @NotNull
    @ManyToOne
    private SharingFormula sharingFormula = new SharingFormula();

    @NotNull
    @ManyToOne
    private Currency currency = new Currency();

    @JsonBackReference
    @ManyToMany(mappedBy = "items")
    private Set<Category> categories = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private Set<Promotion> promotions = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private Set<Transaction> transactions = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private Set<TransactionDemo> transactionDemos = new HashSet<>();
}