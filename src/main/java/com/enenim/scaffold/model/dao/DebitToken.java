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
@Table(name = "debit_tokens", uniqueConstraints = @UniqueConstraint(columnNames = {"consumer_id", "payment_method_id", "signature"}))
public class DebitToken extends BaseModel {

    @Column(length = 100)
    private String signature;

    @Column(columnDefinition = "TEXT")
    private String payload;

    @Column(length = 100)
    @JsonProperty("reported_token_id")
    private String reportedTokenId;

    @JsonProperty("transaction_count")
    private int transactionCount;

    private EnabledStatus enabled = EnabledStatus.ENABLED;

    @ManyToOne
    private Consumer consumer;

    @ManyToOne
    private PaymentMethod paymentMethod;

    @JsonBackReference
    @OneToMany(mappedBy = "debitToken", fetch = FetchType.LAZY)
    private Set<RecurringPayment> recurringPayments = new HashSet<>();
}