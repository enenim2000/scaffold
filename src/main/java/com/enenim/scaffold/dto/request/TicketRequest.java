package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.model.dao.Ticket;
import com.enenim.scaffold.util.ObjectMapperUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class TicketRequest extends RequestBody<Ticket>{

	@NotBlank(message = "@{ticket.transaction_reference.required")
	@JsonProperty("transaction_reference")
	private String transactionReference;

	@JsonProperty("consumer_id")
	@NotBlank(message = "@{consumer_id.required")
	private Long consumerId;

	@NotBlank(message = "@{ticket.subject.required")
	private String subject;

	@NotBlank(message = "@{ticket.comment.required")
	private String comment;

	@JsonProperty("service_id")
	@NotBlank(message = "@{service_id.required")
	private Long serviceId;

	@Override
	public Ticket buildModel() {
		return ObjectMapperUtil.map(this, Ticket.class);
	}

	@Override
	public Ticket buildModel(Ticket ticket) {
		return ObjectMapperUtil.map(this, ticket);
	}
}
