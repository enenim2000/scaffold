package com.enenim.scaffold.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ServiceRequest {

    @JsonProperty("service_id")
    private Long serviceId;

    @JsonProperty("event_date")
    private Date eventDate;

    @JsonProperty("event_location")
    private Date eventLocation;
}
