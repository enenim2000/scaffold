package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "biller_settings")
public class BillerSetting extends BaseModel {
    @JsonProperty("key")
    @NotNull
    @Column(length = 40)
    private String _key;

    @JsonProperty("value")
    @NotNull
    private String _value;

    @NotNull
    @ManyToOne
    private Biller biller;
}