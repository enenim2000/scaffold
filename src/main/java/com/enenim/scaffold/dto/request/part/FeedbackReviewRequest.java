package com.enenim.scaffold.dto.request.part;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FeedbackReviewRequest {
    @JsonProperty("service_id")
    private Long serviceId;

    @JsonProperty("rating")
    private String rating;

    @JsonProperty("comment")
    private String comment;
}
