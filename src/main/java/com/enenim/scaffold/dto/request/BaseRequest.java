package com.enenim.scaffold.dto.request;

import lombok.Data;

import javax.validation.Valid;

@Data
public class BaseRequest<T extends RequestBody> {
    private String ipAddress;
    private String userAgent;

    @Valid
    private T data;
}
