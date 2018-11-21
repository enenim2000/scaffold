package com.enenim.scaffold.shared;

import com.enenim.scaffold.util.message.ValidationMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class ErrorValidator {

    private HashMap<String, List<Object>> errorObject;

    public static Map<String, List<Map<String, String>>> errors (MethodArgumentNotValidException ex){
        Map<String, List<Map<String, String>>> errors = new HashMap<>();
        for(FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            String messagePropertyKey = fieldError.getDefaultMessage();
            if(messagePropertyKey.contains("@{")){
                String messageKey = messagePropertyKey.replace("@{", "").replace("}", "");
                String[] array = messageKey.split("\\.");
                String rule = array[2];
                String fieldName = array[1];
                if(StringUtils.isEmpty(errors.get(fieldName))){
                    errors.put(fieldName, new ArrayList<>());
                }
                errors.get(fieldName).add(
                    new HashMap<String, String>(){{put("rule", rule); put("message", ValidationMessage.msg(messageKey));}}
                );
            }
        }
        return errors.keySet().isEmpty() ? null : errors;
    }
}
