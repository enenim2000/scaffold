package com.enenim.scaffold.dto.response;

import com.enenim.scaffold.enums.TicketStatus;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TicketResponse extends BaseModel {

    @JsonProperty("transaction_reference")
    private String transactionReference;

    private String subject;

    @JsonProperty("last_comment")
    private String lastComment;

    @JsonProperty("last_comment_user_type")
    private String lastCommentUserType;

    @JsonProperty("last_comment_user_id")
    private String lastCommentUserId;

    private String history;

    private TicketStatus status;
}