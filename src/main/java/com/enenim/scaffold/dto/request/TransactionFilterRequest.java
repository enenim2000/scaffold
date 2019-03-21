package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TransactionFilterRequest {

	@JsonProperty("start_date")
	private String startDate;

	@JsonProperty("end_date")
	private String endDate;

	@JsonProperty("status")
	private TransactionStatus status;

	@JsonProperty("search_term")
	private String searchTerm;

}
