package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "beneficiary_settings", uniqueConstraints = @UniqueConstraint(columnNames = {"beneficiary_id", "_key"}))
public class BeneficiarySetting extends BaseModel {

    @NotNull
    @OneToOne
    private Beneficiary beneficiary;

    @JsonProperty("key")
    @NotNull
    private String _key;

    @JsonProperty("value")
    @NotNull
    private String _value;
}