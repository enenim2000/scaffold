package com.enenim.scaffold.dto.request;

import lombok.Data;

@Data
abstract class RequestBody<T> {
    abstract T validateRequest();
    abstract T validateRequest(T data);
}
