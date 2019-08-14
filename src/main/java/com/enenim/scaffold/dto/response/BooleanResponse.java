package com.enenim.scaffold.dto.response;

import com.enenim.scaffold.util.RequestUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BooleanResponse{

    private String message;

    private Boolean data;

    public BooleanResponse(Object data) {
        setMessage(RequestUtil.getMessage());
        setData((Boolean) data);
    }
}