package com.enenim.scaffold.dto.response;

import com.enenim.scaffold.util.ErrorUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ExceptionResponse {
    @JsonProperty("error_code")
    private String errorCode;

    @JsonProperty("error_message")
    private String errorMessage;

    @JsonProperty("errors")
    private Object errors = null;

    public void setMessage(String message){
        this.setErrorMessage(message);
    }

    public void setMessage(Exception ex, String ... params){
        String msgPrefix = "";
        if(params.length > 0){
            msgPrefix = params[0];
        }
        if(ex.getCause() == null){
            List<String> errorTrace = ErrorUtil.getErrorTrace(ex.getStackTrace());
            if(!errorTrace.isEmpty()){
                this.setErrorMessage(msgPrefix + errorTrace.get(0));
            }
        }else {
            this.setErrorMessage(msgPrefix + ex.getCause().getMessage());
        }
    }
}