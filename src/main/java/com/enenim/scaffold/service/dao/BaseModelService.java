package com.enenim.scaffold.service.dao;

public abstract class BaseModelService<RequestBody> {
    public abstract void validateDependencies(RequestBody request);
}
