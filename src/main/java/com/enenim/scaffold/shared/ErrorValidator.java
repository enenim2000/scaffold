package com.enenim.scaffold.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorValidator {

    private HashMap<String, List<Object>> errorObject;

    public ErrorValidator(){
        errorObject = new HashMap<>();
    }

    public ErrorValidator addError(HashMap<String, Object> error){
        List<Object> errors;
        Object value;

        if(error != null && !error.keySet().isEmpty()){
            for (Map.Entry<String, Object> pair : error.entrySet()){
                 errors = this.errorObject.get(pair.getKey());
                if(errors != null){
                    value = error.get(pair.getKey());
                    if(value != null){
                        errors.add(error.get(pair.getKey()));
                        this.errorObject.put(pair.getKey(), errors);
                    }
                }else {
                    List<Object> errorList = new ArrayList<>();
                    value = error.get(pair.getKey());
                    if(value != null){
                        errorList.add(value);
                        errors = errorList;
                        this.errorObject.put(pair.getKey(), errors);
                    }
                }
            }
        }

        return this;
    }

    public ErrorValidator addError(String field, String validationType, String errorMessage){
        HashMap<String, Object> errors = new HashMap<>();
        HashMap<String, String> error = new HashMap<>();
        error.put(validationType, errorMessage);
        errors.put(field, error);
        return addError(errors);
    }

    public HashMap<String, List<Object>> build(){
        return this.errorObject;
    }
}
