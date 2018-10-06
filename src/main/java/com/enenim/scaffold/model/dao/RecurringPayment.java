package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.enums.Staged;
import com.enenim.scaffold.interfaces.DataTypeConstant;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.sql.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "recurring_payments", uniqueConstraints = @UniqueConstraint(columnNames = {"customerId", "frequencyInSecs", "currency_id", "amount", "consumer_id", "subscriptionType", "subscriptionId"}))
public class RecurringPayment extends BaseModel {

    @NotNull
    @Column(length = 30)
    @JsonProperty("subscription_type")
    private String subscriptionType;

    @NotNull
    @Column(length = 10)
    @JsonProperty("subscription_id")
    private Integer subscriptionId;

    @NotNull
    @Column(length = 60)
    private String title;

    @NotNull
    private Double amount;

    @NotNull
    @JsonProperty("customer_id")
    @Column(length = 50)
    private String customerId;

    @Column(columnDefinition = DataTypeConstant.TEXT)
    private String payloads = "";

    @NotNull
    private Integer loops;

    @Column(length = 20)
    private String frequency;

    @Column(length = 20)
    @JsonProperty("frequency_in_secs")
    private BigInteger frequencyInSecs;

    @Column(length = 20)
    private BigInteger reminder;

    @NotNull
    @JsonProperty("next_renewal_date")
    private Date nextRenewalDate;


    @JsonProperty("successful_attempts")
    private Integer successfulAttempts;


    @JsonProperty("failed_attempts")
    private Integer failedAttempts;

    private Staged staged = null;

    @Column(length = 20)
    private String reserved;


    private Integer attempts;

    @NotNull
    private EnabledStatus enabled = EnabledStatus.ENABLED;

    @NotNull
    @ManyToOne
    private Consumer consumer;

    @NotNull
    @ManyToOne
    private Currency currency;

    @NotNull
    @ManyToOne
    private DebitToken debitToken;

    @JsonBackReference
    @OneToMany(mappedBy = "recurringPayment", fetch = FetchType.LAZY)
    private Set<Transaction> transactions = new HashSet<>();

    public Map<String, Object> requestFilter(Map<String, Object> data) {
        return data;
    }
}