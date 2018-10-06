package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.Initiator;
import com.enenim.scaffold.enums.SentSurcharge;
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

    @ManyToOne
    private RecurringPayment recurringPayment;

    @Column(length = 100)
    @JsonProperty("payment_channel_reference")
    private String paymentChannelReference;

    @NotNull
    @Column(unique = true, length = 50)
    @JsonProperty("transaction_reference")
    private String transactionReference;

    @NotNull
    @Column(length = 50)
    @JsonProperty("customer_id")
    private String customerId;

    @NotNull
    private TransactionStatus status = TransactionStatus.PENDING;

    @Column(length = 10)
    @JsonProperty("reported_status_code")
    private String reportedStatusCode;

    @Column(length = 60)
    @JsonProperty("reported_status_message")
    private String reportedStatusMessage;

    @ManyToOne
    private Settlement settlement;

    @NotNull
    @Column(precision=20, scale=4)
    private Double amount;

    @NotNull
    @Column(precision=20, scale=4)
    @JsonProperty("amount_paid")
    private Double amountPaid;

    @NotNull
    @JsonProperty("biller_discount")
    private Double billerDiscount;

    @NotNull
    @Column(precision=20, scale=4)
    private Double surcharge;

    @NotNull
    @JsonProperty("sent_surcharge")
    private SentSurcharge sentSurcharge = SentSurcharge.SURCHARGE_NOT_SENT;

    @NotNull
    private Double vat;

    @NotNull
    @Column(length = 11)
    private Integer quantity;

    @JsonProperty("date_paid")
    private Date date_paid;

    @JsonProperty("date_reversed")
    private Date dateReversed;

    @JsonProperty("date_settled")
    private Date dateSettled;

    @NotNull
    @ManyToOne
    private Biller biller;

    @NotNull
    @ManyToOne
    private Item item;

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

    @JsonBackReference
    @OneToOne(mappedBy = "transaction")
    private ReversalRequest reversalRequest;

    @JsonBackReference
    @OneToMany(mappedBy = "transaction", fetch = FetchType.LAZY)
    private Set<TransactionChargeSplit> transactionChargeSplits = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "transaction", fetch = FetchType.LAZY)
    private Set<TransactionDispute> transactionDisputes = new HashSet<>();

    @JsonBackReference
    @OneToOne(mappedBy = "transaction", fetch = FetchType.LAZY)
    private TransactionExtra transactionExtras;
}