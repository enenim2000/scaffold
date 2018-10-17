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
public class StringResponse {

    private String message;

    private String data;

    @JsonProperty("meta_data")
    private MetaData metaData = null;

    public StringResponse(Object data) {
        setMessage(RequestUtil.getMessage());
        setData((String) data);
        setMetaData(null);
    }
}