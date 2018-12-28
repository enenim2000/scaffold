package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.BeneficiaryType;
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
@Table(name = "beneficiaries", uniqueConstraints = @UniqueConstraint(columnNames = {"account_provider_id", "accountNumber", "name"}))
public class Beneficiary extends BaseModel {

    @Transient
    public static String searchables = "accountName,accountNumber,email,name";

    @Transient
    public static String table = "beneficiaries";

    @Transient
    public static String references = "accountProvider:account_providers";

    @NotNull
    @Column(length = 100)
    private String name;

    @NotNull
    @OneToOne
    @JsonProperty("account_provider")
    @JoinColumn(name = "account_provider_id")
    private AccountProvider accountProvider = null;

    @NotNull
    @Column(length = 100)
    @JsonProperty("account_number")
    private String accountNumber;

    @NotNull
    @JsonProperty("account_name")
    private String accountName;

    @NotNull
    @Column(unique = true, length = 150)
    private String email;

    @NotNull
    private EnabledStatus enabled = EnabledStatus.ENABLED;

    @NotNull
    private BeneficiaryType type = BeneficiaryType.REGULAR;

    @JsonBackReference
    @ManyToMany
    @JoinTable(name = "beneficiary_biller",
            joinColumns = @JoinColumn(name = "beneficiary_id"),
            inverseJoinColumns = @JoinColumn(name = "biller_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"beneficiary_id", "biller_id"})
    )
    private Set<Biller> billers = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "beneficiary", fetch = FetchType.LAZY)
    private Set<BeneficiarySetting> beneficiarySettings = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "beneficiary", fetch = FetchType.LAZY)
    private Set<TransactionChargeBeneficiary> transactionChargeBeneficiaries = new HashSet<>();
}
