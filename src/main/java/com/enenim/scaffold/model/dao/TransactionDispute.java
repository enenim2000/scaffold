package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.TransactionDisputeStatus;
import com.enenim.scaffold.interfaces.DataTypeConstant;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
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
@Table(name = "transaction_disputes")
public class TransactionDispute extends BaseModel {

    @NotNull
    @Column(unique = true)
    @JsonProperty("reference_number")
    private String referenceNumber;

    @NotNull
    @JsonProperty("transaction_reference")
    private String transactionReference;

    @NotNull
    @Column(columnDefinition = DataTypeConstant.TEXT)
    private String message = "";

    @Column(columnDefinition = DataTypeConstant.TEXT)
    private String remark = "";

    @NotNull
    private TransactionDisputeStatus status = TransactionDisputeStatus.OPEN;

    @NotNull
    @ManyToOne
    private Transaction transaction;

    @NotNull
    @ManyToOne
    @JsonBackReference
    @ApiModelProperty(required = true, hidden = true)
    private Consumer consumer;
}