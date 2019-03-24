package com.enenim.scaffold.dto.response;

import com.enenim.scaffold.model.BaseModel;
import com.enenim.scaffold.model.dao.Feedback;
import com.enenim.scaffold.shared.FeedbackReview;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class FeedbackResponse extends BaseModel{

    public FeedbackResponse(){}

    public FeedbackResponse(Feedback feedback){
        FeedbackResponse feedbackResponse = feedback.getFeedbackResponse();
        setTransactionReference(feedbackResponse.getTransactionReference());
        setSubject(feedbackResponse.getSubject());
        setReviews(feedbackResponse.getReviews());
    }

    @JsonProperty("transaction_reference")
    private String transactionReference;

    private String subject;

    private List<FeedbackReview> reviews;
}