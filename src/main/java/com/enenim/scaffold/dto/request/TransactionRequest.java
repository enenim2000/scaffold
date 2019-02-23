package com.enenim.scaffold.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class TransactionRequest {

    /**
     * This field will be use for populating ServiceForm table
     */
    private List<ServiceRequest> services;

    private Date date;

    @JsonProperty("start_time")
    private LocalTime startTime;

    /**
     * Duration in minutes
     */
    private Long duration;

    private String location;

    @JsonProperty("consumer_id")
    private Long consumerId;

}
