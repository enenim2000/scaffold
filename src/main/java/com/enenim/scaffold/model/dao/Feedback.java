package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.dto.response.FeedbackResponse;
import com.enenim.scaffold.model.BaseModel;
import com.enenim.scaffold.shared.FeedbackReview;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    private static List<FeedbackReview> getReviews(String json){
        try {
            return new ObjectMapper().readValue(json, new TypeReference<List<FeedbackReview>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public FeedbackResponse getFeedbackResponse(){
        FeedbackResponse feedbackResponse = new FeedbackResponse();
        feedbackResponse.setSubject(getSubject());
        feedbackResponse.setTransactionReference(getTransactionReference());
        feedbackResponse.setReviews( getReviews(getReview()) );
        return feedbackResponse;
    }
}