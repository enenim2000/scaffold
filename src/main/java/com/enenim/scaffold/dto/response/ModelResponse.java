package com.enenim.scaffold.dto.response;

import com.enenim.scaffold.util.RequestUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ModelResponse<T> {

    private String message;

    private T data;

    public ModelResponse(T data) {
        setMessage(RequestUtil.getMessage());
        setData(data);
    }
}