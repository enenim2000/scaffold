package com.enenim.scaffold.shared;

import com.enenim.scaffold.constant.ValidationConstant;
import com.enenim.scaffold.util.message.ValidationMessage;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    private static final String EMAIL = "email";
    private static final String PHONE_NUMBER = "phone_number";
    private HashMap<String, List<Object>> errors = null;

    public Validation(HashMap<String, List<Object>> errors){
        this.errors = errors;
    }

    public HashMap<String, List<Object>> errors() {
        if(errors != null) return errors;
        return new HashMap<>();
    }

    public boolean hasErrors(){
        return !errors.isEmpty();
    }

    private static boolean isValidEmail(String email){
        if (email != null) {
            String regex = ValidationConstant.VALIDATION_EMAIL_FORMAT;
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                return true;
            }
        }

        return false;
    }

    private static boolean isValidPhoneNumber(String phone_number){
        if (phone_number != null) {
            String regex = ValidationConstant.VALIDATION_PHONE_NUMBER_FORMAT;
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(phone_number);
            if (matcher.matches()) {
                return true;
            }
        }

        return false;
    }

    private static boolean isBlank(String value){
        return (value == null || value.equals(ValidationConstant.EMPTY_STRING) || value.equals("null") || value.trim().equals(""));
    }

    private static boolean isNumberic(String value){
        boolean state = false;
       if(!isBlank(value)){
           state = value.matches("^[0-9]+$");
       }

       return state;
    }

    private static boolean isFloat(String value){
        if (value != null) {
            String regex = ValidationConstant.VALIDATION_NUMBER_FLOAT_FORMAT;
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(value);
            if (matcher.matches()) {
                return true;
            }
        }

        return false;
    }

    /**
     *This method is used getTo get message for fields with different min and/or max length requirement
     */
    private static Object getMessage(String modelKey, String fieldName, String validationType, int ... minAndMaxLength){
        HashMap<String, String> message = new HashMap<>();
        String key = modelKey.concat(ValidationConstant.VALIDATION_FIELD_SEPARATOR).concat(fieldName).concat(ValidationConstant.VALIDATION_FIELD_SEPARATOR).concat(validationType);
        String validationMessage = ValidationMessage.msg(key);

            int firstIndex = 0;
            int secondIndex = 1;
            if(minAndMaxLength.length == 1){
                validationMessage = validationMessage.replaceFirst(ValidationConstant.VALIDATION_PLACE_HOLDER, String.valueOf(minAndMaxLength[firstIndex]));
            }else if(minAndMaxLength.length == 2){
                validationMessage = validationMessage.replaceFirst(ValidationConstant.VALIDATION_PLACE_HOLDER, String.valueOf(minAndMaxLength[firstIndex]))
                        .replaceFirst(ValidationConstant.VALIDATION_PLACE_HOLDER, String.valueOf(minAndMaxLength[secondIndex]));
            }

            if(validationType.equalsIgnoreCase(ValidationConstant.TYPE_INVALID)) {
                message.put(validationType, validationMessage);
            }else {
                message.put(validationType, validationMessage);
            }

        return message;
    }

    public static HashMap<String, Object> validateInput(String value, String modelKey, String inputName, String type, int ... minAndMaxLength){
        Object error = null;

        boolean isNull = value == null;
        boolean isEmpty = true;
        int inputLength = 0;

        if(!isNull) {
            inputLength = value.trim().length();
            isEmpty = value.trim().isEmpty();
        }

        if( ValidationConstant.TYPE_REQUIRED.equalsIgnoreCase(type) && (isNull || isEmpty) ){
            error = Validation.getMessage(modelKey, inputName, ValidationConstant.TYPE_REQUIRED);
        }else if( ValidationConstant.TYPE_REQUIRED.equalsIgnoreCase(type) && (!isNull && isEmpty) ){
            error = Validation.getMessage(modelKey, inputName, ValidationConstant.TYPE_REQUIRED);
        }else if( ValidationConstant.TYPE_NUMBER.equalsIgnoreCase(type) && !isNumberic(value) ){
            error = Validation.getMessage(modelKey, inputName, ValidationConstant.TYPE_NUMBER);
        }else if( ValidationConstant.TYPE_INTEGER.equalsIgnoreCase(type) && !isNumberic(value) ){
            error = Validation.getMessage(modelKey, inputName, ValidationConstant.TYPE_INTEGER);
        }else if( ValidationConstant.TYPE_FLOAT.equalsIgnoreCase(type) && !isFloat(value) ){
            error = Validation.getMessage(modelKey, inputName, ValidationConstant.TYPE_FLOAT);
        }else if(EMAIL.equalsIgnoreCase(inputName) && ValidationConstant.TYPE_PATTERN.equalsIgnoreCase(type)){
            if(!isValidEmail(value)){
                error = Validation.getMessage(modelKey, inputName, ValidationConstant.TYPE_PATTERN);
            }
        }else if(PHONE_NUMBER.equalsIgnoreCase(inputName) && ValidationConstant.TYPE_PATTERN.equalsIgnoreCase(type)){
            if(!isNull){
                if( !isValidPhoneNumber(value) ){
                    error = Validation.getMessage(modelKey, inputName, ValidationConstant.TYPE_PATTERN);
                }
            }
        }else if( ValidationConstant.TYPE_MIN.equalsIgnoreCase(type) && minAndMaxLength.length == 1 ){
            if(!isNull && inputLength < minAndMaxLength[0]){
                error = Validation.getMessage(modelKey, inputName, ValidationConstant.TYPE_MIN, minAndMaxLength[0]);
            }
        }else if( ValidationConstant.TYPE_MAX.equalsIgnoreCase(type) && minAndMaxLength.length == 1 ){
            if(!isNull && inputLength < minAndMaxLength[0]){
                error = Validation.getMessage(modelKey, inputName, ValidationConstant.TYPE_MAX, minAndMaxLength[0]);
            }
        }else if( ValidationConstant.TYPE_MINMAX.equalsIgnoreCase(type) && minAndMaxLength.length == 2 ){
            if(!isNull){
                if( !(inputLength >= minAndMaxLength[0] && inputLength <= minAndMaxLength[1]) ){
                    error = Validation.getMessage(modelKey, inputName, ValidationConstant.TYPE_MINMAX, minAndMaxLength[0], minAndMaxLength[1]);
                }
            }
        }

        HashMap<String, Object> errorObject = new HashMap<>();
        errorObject.put(inputName, error);

       return errorObject;
    }
}
