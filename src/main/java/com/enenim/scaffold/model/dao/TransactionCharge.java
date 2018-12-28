package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.ChargeSourceType;
import com.enenim.scaffold.enums.DebitFlag;
import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.enums.TransactionChargeType;
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
@Table(name = "transaction_charges")
public class TransactionCharge extends BaseModel {

    public TransactionCharge() {
    }

    public TransactionCharge(Long id) {
        super();
        this.setId(id);
    }

    @NotNull
    @ManyToOne
    @JoinColumn(name="sharing_formula_id")
    private SharingFormula sharingFormula = new SharingFormula();

    @ManyToOne
    @JoinColumn(name="payment_channel_id")
    private PaymentChannel paymentChannel = new PaymentChannel();

    @ManyToOne
    @JoinColumn(name="profile_id")
    private Profile profile = new Profile();

    private TransactionChargeType type = TransactionChargeType.EMPTY;

    @NotNull
    @JsonProperty("charge_source")
    private ChargeSourceType chargeSource = ChargeSourceType.AMOUNT;


    @JsonProperty("value")
    private Double _value = 0.00;

    @NotNull
    @JsonProperty("value_floor")
    private Double valueFloor = 0.0;

    @NotNull
    @JsonProperty("value_ceiling")
    private Double valueCeiling = 0.0;

    @NotNull
    private EnabledStatus enabled = EnabledStatus.ENABLED;

    @NotNull
    @JsonProperty("debit_flag")
    private DebitFlag debitFlag = DebitFlag.EMPTY;

    @NotNull
    @JsonProperty("order")
    @Column(length = 4)
    private Integer _order = 0;

    @JsonBackReference
    @OneToMany(mappedBy = "transactionCharge", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<TransactionChargeBeneficiary> transactionChargeBeneficiaries = new HashSet<>();
}