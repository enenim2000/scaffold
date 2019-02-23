package com.enenim.scaffold.model.dao;

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
@Table(name = "service_form")
public class ServiceForm extends BaseModel {

    @NotNull
    @ManyToOne
    private Service service;

    /**
     * This field stores the edited payload form presented to the user/consumer
     */
    private String form;

    @JsonBackReference
    @ManyToMany
    @JoinTable(name = "transaction_service_form",
            joinColumns = @JoinColumn(name = "service_form_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"service_form_id", "transaction_id"})
    )
    private Set<Transaction> transactions = new HashSet<>();
}