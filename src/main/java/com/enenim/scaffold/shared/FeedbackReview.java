package com.enenim.scaffold.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FeedbackReview {
    @JsonProperty("service_id")
    private String serviceId;

    @JsonProperty("rating")
    private String rating;

    @JsonProperty("comment")
    private String comment;
}
