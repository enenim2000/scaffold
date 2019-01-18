package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.enums.VerifyStatus;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "consumers")
public class Consumer extends BaseModel {

    @JsonProperty("first_name")
    @SerializedName("first_name")
    @Column(length = 30)
    private String firstName;

    @JsonProperty("last_name")
    @SerializedName("last_name")
    @Column(length = 30)
    private String lastName;

    @NotNull
    @JsonProperty("email")
    @SerializedName("email")
    @Column(unique = true, length = 100)
    private String email;

    @JsonProperty("phone_number")
    @SerializedName("phone_number")
    @Column(length = 30)
    private String phoneNumber;

    @JsonProperty("date_of_birth")
    @SerializedName("date_of_birth")
    @Column(length = 10)
    private String dateOfBirth;

    private VerifyStatus verified = VerifyStatus.NOT_VERIFIED;

    private EnabledStatus enabled = EnabledStatus.DISABLED;

    @JsonBackReference
    @OneToMany(mappedBy = "consumer", fetch = FetchType.LAZY)
    private Set<ConsumerSetting> consumerSettings = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "consumer", fetch = FetchType.LAZY)
    private Set<DebitToken> debitTokens = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "consumer", fetch = FetchType.LAZY)
    private Set<RecurringPayment> recurringPayments = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "consumer", fetch = FetchType.LAZY)
    private Set<Transaction> transactions = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "consumer", fetch = FetchType.LAZY)
    private Set<TransactionDemo> transactionDemos = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "consumer", fetch = FetchType.LAZY)
    private Set<TransactionDispute> transactionDisputes = new HashSet<>();
}
