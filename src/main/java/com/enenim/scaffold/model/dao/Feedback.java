package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "feedbacks")
@ToString
public class Feedback extends BaseModel {

    @NotNull
    @Column(unique = true, length = 100)
    @JsonProperty("transaction_reference")
    private String transactionReference;

    @NotNull
    @ManyToOne
    private Consumer consumer;

    @NotNull
    private String subject;

    @Lob
    private String review;
}