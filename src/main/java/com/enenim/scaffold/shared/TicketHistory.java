package com.enenim.scaffold.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TicketHistory {

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("user_type")
    private String userType;

    @JsonProperty("service_id")
    private String serviceId;

    private String comment;

    private String date;
}
