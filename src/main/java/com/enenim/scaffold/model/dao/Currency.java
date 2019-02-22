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
@Table(name = "currencies")
public class Currency extends BaseModel {

    @NotNull
    @Column(unique = true, length = 10)
    private String code;

    @NotNull
    @Column(unique = true, length = 100)
    private String name = "";

    @NotNull
    @Column(unique = true, length = 10)
    private String html;

    @JsonProperty("code_digit")
    private Integer codeDigit = 0;

    @NotNull
    private EnabledStatus enabled = EnabledStatus.ENABLED;

    @JsonBackReference
    @OneToMany(mappedBy = "currency", fetch = FetchType.LAZY)
    private Set<Transaction> transactions = new HashSet<>();
}