package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.AmountType;
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
@Table(name = "services", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"vendor_id", "code"}),
        @UniqueConstraint(columnNames = {"vendor_id", "name"})
})
public class Service extends BaseModel {

    @NotNull
    @Column(length = 50)
    private String name;

    @NotNull
    @Column(length = 20)
    private String code;

    @NotNull
    @JsonProperty("amount_type")
    private AmountType amountType = AmountType.VARIABLE_AMOUNT;

    @NotNull
    @Column(precision=20, scale=4)
    private Double amount = 0.00;

    @NotNull
    @Column(precision=20, scale=4)
    private Double surcharge = 0.00;

    @NotNull
    @Column(precision=20, scale=4)
    private Double discount = 0.00;

    @NotNull
    private String description;

    @NotNull
    @Column(unique = true, length = 200)
    private String slug = "";

    @NotNull
    private EnabledStatus enabled = EnabledStatus.ENABLED;

    @NotNull
    @ManyToOne
    private Vendor vendor;

    @NotNull
    @ManyToOne
    private Currency currency = new Currency();

    @JsonBackReference
    @ManyToMany
    @JoinTable(name = "transaction_services",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"service_id", "transaction_id"})
    )
    private Set<Transaction> transactions = new HashSet<>();

    @JsonBackReference
    @ManyToMany(mappedBy = "services")
    private Set<Category> categories = new HashSet<>();
}