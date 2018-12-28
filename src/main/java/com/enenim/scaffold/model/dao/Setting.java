package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "settings")
public class Setting extends BaseModel{

    @NotNull
    @Column(length = 50, name = "_key")
    private String key;

    @NotNull
    @Column(name = "_value")
    private String value;
}