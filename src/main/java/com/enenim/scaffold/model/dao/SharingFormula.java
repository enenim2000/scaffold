package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "sharing_formulas", uniqueConstraints = @UniqueConstraint(columnNames ={"vendor_id", "name"}))
public class SharingFormula extends BaseModel {
    public SharingFormula() {
    }

    public SharingFormula(Long id) {
        super();
        this.setId(id);
    }

    @ManyToOne
    private Vendor vendor = null;

    @NotNull
    @ManyToOne
    private Currency currency = null;

    @NotNull
    private String name;

    @NotNull
    private EnabledStatus enabled = EnabledStatus.ENABLED;

    @JsonBackReference
    @OneToMany(mappedBy = "sharingFormula", fetch = FetchType.EAGER)
    private Set<TransactionCharge> transactionCharges = new HashSet<>();
}