package com.enenim.scaffold.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FeedbackReview {
    @JsonProperty("service_id")
    private Long serviceId;

    @JsonProperty("service_name")
    private String serviceName;

    @JsonProperty("rating")
    private String rating;

    @JsonProperty("comment")
    private String comment;
}
