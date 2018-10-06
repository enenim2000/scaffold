package com.enenim.scaffold.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;

@Data
public class BaseRequest<T extends RequestBody> {

    @JsonProperty("ip_address")
    private String ipAddress;

    @JsonProperty("user_agent")
    private String userAgent;

    @Valid
    private T data;
}
