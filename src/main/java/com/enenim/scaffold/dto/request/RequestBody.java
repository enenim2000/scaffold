package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.shared.Validation;
import lombok.Data;

@Data
abstract class RequestBody<T> {
    abstract T buildModel();
    abstract T buildModel(T model);
    abstract Validation validateRequest();
}
