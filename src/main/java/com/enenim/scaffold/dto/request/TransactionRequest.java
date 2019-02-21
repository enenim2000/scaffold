package com.enenim.scaffold.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TransactionRequest {

    private List<ServiceRequest> services;

    @JsonProperty("consumer_id")
    private Long consumerId;

}
