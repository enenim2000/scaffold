package com.enenim.scaffold.dto.response;

import com.enenim.scaffold.shared.MetaData;
import com.enenim.scaffold.util.RequestUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ModelResponse<T> {

    private String message;

    private T data;

    @JsonProperty("meta_data")
    private MetaData metaData = null;

    public ModelResponse(T data) {
        setMessage(RequestUtil.getMessage());
        setData(data);
        setMetaData(null);
    }
}