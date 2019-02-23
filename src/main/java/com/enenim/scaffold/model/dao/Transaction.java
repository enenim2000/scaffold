package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.Initiator;
import com.enenim.scaffold.enums.TransactionStatus;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction extends BaseModel {

    @NotNull
    private Initiator initiator;

    @Column(length = 100)
    @JsonProperty("payment_channel_reference")
    private String paymentChannelReference;

    @NotNull
    @Column(unique = true, length = 50)
    @JsonProperty("transaction_reference")
    private String transactionReference;

    @NotNull
    private TransactionStatus status = TransactionStatus.PENDING;

    @NotNull
    @Column(precision=20, scale=4)
    @JsonProperty("amount_paid")
    private Double amountPaid;

    @NotNull
    @JsonProperty("vendor_discount")
    private Double vendorDiscount;

    @NotNull
    @Column(precision=20, scale=4)
    private Double surcharge;

    @NotNull
    private Double vat;

    @JsonProperty("date_paid")
    private Date date_paid;

    @JsonProperty("date_reversed")
    private Date dateReversed;

    @JsonProperty("date_completed")
    private Date dateCompleted;

    /*@NotNull
    @ManyToOne
    private Service service;*/

    @JsonBackReference
    @ManyToMany(mappedBy = "transactions")
    private Set<ServiceForm> serviceForms = new HashSet<>();

    @ManyToOne
    private Consumer consumer;

    @ManyToOne
    private Branch branch;

    @ManyToOne
    private Staff staff;

    @NotNull
    @ManyToOne
    private Currency currency;

    @NotNull
    @ManyToOne
    private PaymentChannel paymentChannel;

    @NotNull
    @ManyToOne
    private PaymentMethod paymentMethod;
}