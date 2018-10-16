package com.enenim.scaffold.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

@Getter
@Setter
@ToString
public class BaseResponse<T> extends ResponseEntity<T> {

    public BaseResponse() {
        super(HttpStatus.OK);
    }

    public BaseResponse(T body) {
        super(body, HttpStatus.OK);
    }

    public BaseResponse(MultiValueMap<String, String> headers) {
        super(headers, HttpStatus.OK);
    }

    public BaseResponse(T body, MultiValueMap<String, String> headers) {
        super(body, headers, HttpStatus.OK);
    }
}