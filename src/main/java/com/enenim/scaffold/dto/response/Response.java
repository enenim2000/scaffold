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
public class Response<T> extends ResponseEntity<T> {

    public Response() {
        super(HttpStatus.OK);
    }

    public Response(T body) {
        super(body, HttpStatus.OK);
    }

    public Response(MultiValueMap<String, String> headers) {
        super(headers, HttpStatus.OK);
    }

    public Response(T body, MultiValueMap<String, String> headers) {
        super(body, headers, HttpStatus.OK);
    }
}