package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@ToString
@Table(name = "settings")
public class Setting extends BaseModel{

    @NotNull
    @Column(length = 50, name = "_category")
    @JsonProperty("category_key")
    private String categoryKey;

    @NotNull
    @Column(length = 50, name = "_key", unique = true)
    @JsonProperty("setting_key")
    private String settingKey;

    @NotNull
    @Column(name = "_value")
    private String value;

}