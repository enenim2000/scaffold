package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.TicketStatus;
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
@Table(name = "tickets")
@ToString
public class Ticket extends BaseModel {

    @NotNull
    @Column(unique = true, length = 100)
    @JsonProperty("transaction_reference")
    private String transactionReference;

    @ManyToOne
    @NotNull
    private Consumer consumer;

    @NotNull
    private String subject;

    @NotNull
    @JsonProperty("last_comment")
    private String lastComment;

    @NotNull
    @JsonProperty("last_comment_user_type")
    private String lastCommentUserType;

    @NotNull
    @JsonProperty("last_comment_user_id")
    private Long lastCommentUserId;

    @Lob
    private String history;

    @NotNull
    private TicketStatus status;
}