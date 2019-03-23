package com.enenim.scaffold.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class TicketCommentRequest{

	@NotBlank(message = "@{ticket.transaction_reference.required")
	@JsonProperty("transaction_reference")
	private String transactionReference;

	@NotBlank(message = "@{ticket.comment.required")
	private String comment;

	@JsonProperty("service_id")
	@NotBlank(message = "@{service_id.required")
	private Long serviceId;

}
