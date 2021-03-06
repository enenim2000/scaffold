package com.enenim.scaffold.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;

@Data
public class Request<T extends RequestBody> {

    @JsonProperty("ip_address")
    private String ipAddress;

    @JsonProperty("user_agent")
    private String userAgent;

    @Valid
    @JsonProperty("data")
    private T body;
}
