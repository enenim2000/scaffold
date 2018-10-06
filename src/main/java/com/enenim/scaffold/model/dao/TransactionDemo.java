package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.Initiator;
import com.enenim.scaffold.enums.SentSurcharge;
import com.enenim.scaffold.enums.SettledStatus;
import com.enenim.scaffold.enums.TransactionDemoStatus;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "transaction_demos")
public class TransactionDemo extends BaseModel {

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
    private TransactionDemoStatus status = TransactionDemoStatus.PENDING;

    @NotNull
    private SettledStatus settled = SettledStatus.UNSETTLED;

    @NotNull
    private Double amount;

    @NotNull
    @JsonProperty("amount_paid")
    private Double amountPaid;

    @NotNull
    @JsonProperty("biller_discount")
    private Double billerDiscount;

    @NotNull
    private Double surcharge;

    @NotNull
    @JsonProperty("sent_surcharge")
    private SentSurcharge sentSurcharge = SentSurcharge.SURCHARGE_NOT_SENT;

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

    @NotNull
    private Initiator initiator;

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
