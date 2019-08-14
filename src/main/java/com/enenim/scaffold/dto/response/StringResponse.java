package com.enenim.scaffold.dto.response;

import com.enenim.scaffold.util.RequestUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StringResponse {

    private String message;

    private String data;

    public StringResponse(Object data) {
        setMessage(RequestUtil.getMessage());
        setData((String) data);
    }
}