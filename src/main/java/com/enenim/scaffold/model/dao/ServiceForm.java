package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    private String payload;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_id")
    @ApiModelProperty(required = true, hidden = true)
    private Transaction transaction;
}