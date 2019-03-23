package com.enenim.scaffold.dto.response;

import com.enenim.scaffold.enums.TicketStatus;
import com.enenim.scaffold.model.BaseModel;
import com.enenim.scaffold.model.dao.Ticket;
import com.enenim.scaffold.shared.TicketHistory;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TicketResponse extends BaseModel {

    public TicketResponse(Ticket ticket){
        setLastComment(ticket.getLastComment());
        setLastCommentUserId(ticket.getLastCommentUserId());
        setLastCommentUserType(ticket.getLastCommentUserType());
        setSubject(ticket.getSubject());
        setTransactionReference(ticket.getTransactionReference());
        setStatus(ticket.getStatus());
        setHistory(TicketHistory.getTicketHistories(ticket.getHistory()));
    }

    @JsonProperty("transaction_reference")
    private String transactionReference;

    private String subject;

    @JsonProperty("last_comment")
    private String lastComment;

    @JsonProperty("last_comment_user_type")
    private String lastCommentUserType;

    @JsonProperty("last_comment_user_id")
    private Long lastCommentUserId;

    private List<TicketHistory> history;

    private TicketStatus status;
}