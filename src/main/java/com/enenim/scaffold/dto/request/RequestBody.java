package com.enenim.scaffold.dto.request;

import lombok.Data;

@Data
abstract class RequestBody<T> {
    abstract T buildModel();
    abstract T buildModel(T model);
}
