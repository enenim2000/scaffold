package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.enums.TransactionChargeBeneficiariesType;
import com.enenim.scaffold.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "transaction_charge_beneficiaries")
public class TransactionChargeBeneficiary extends BaseModel {

    @NotNull
    @ManyToOne
    private Beneficiary beneficiary;

    @NotNull
    @ManyToOne
    private TransactionCharge transactionCharge;

    @NotNull
    private Double share;

    @NotNull
    private TransactionChargeBeneficiariesType type = TransactionChargeBeneficiariesType.DEFAULT;

    @NotNull
    private EnabledStatus enabled = EnabledStatus.ENABLED;
}
