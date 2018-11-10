package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.EnabledStatus;
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
@Table(name = "dispute_messages")
public class DisputeMessage extends BaseModel {
    @NotNull
    @Column(unique = true)
    private String message;

    @NotNull
    private EnabledStatus enabled = EnabledStatus.ENABLED;
}