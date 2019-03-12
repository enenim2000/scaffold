package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.model.dao.Feedback;
import com.enenim.scaffold.shared.FeedbackReview;
import com.enenim.scaffold.util.ObjectMapperUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@ToString
public class FeedbackRequest extends RequestBody<Feedback>{

	@NotBlank(message = "@{feedback.transaction_reference.required")
	@JsonProperty("transaction_reference")
	private String transactionReference;

	@JsonProperty("consumer_id")
	@NotBlank(message = "@{feedback.consumer_id.required")
	private String consumerId;

	@NotBlank(message = "@{feedback.subject.required")
	private String subject;

	private List<FeedbackReview> reviews;

	@Override
	public Feedback buildModel() {
		return ObjectMapperUtil.map(this, Feedback.class);
	}

	@Override
	public Feedback buildModel(Feedback feedback) {
		return ObjectMapperUtil.map(this, feedback);
	}
}
