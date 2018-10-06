package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "reversal_requests")
public class ReversalRequest extends BaseModel {

    @NotNull
    @ManyToOne
    private Transaction transaction;
}