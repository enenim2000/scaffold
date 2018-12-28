package com.enenim.scaffold.constant;

public interface ValidationConstant {
    String VALIDATION_PLACE_HOLDER = "\\{}";
    String VALIDATION_FIELD_SEPARATOR = ".";
    String VALIDATION_EMAIL_FORMAT = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
    String VALIDATION_PHONE_NUMBER_FORMAT = "(\\+)?[0-9]{11,20}$";
    String VALIDATION_NUMBER_FLOAT_FORMAT = "(([1-9]+\\.[0-9]*)|([1-9]*\\.[0-9]+)|([1-9]+))([eE][-+]?[0-9]+)?";

    String TYPE_NUMBER = "number";
    String TYPE_INTEGER = "integer";
    String TYPE_FLOAT = "float";
    String TYPE_MIN = "min";
    String TYPE_MAX = "max";
    String TYPE_MINMAX = "minmax";
    String TYPE_REQUIRED = "required";
    String TYPE_INVALID = "invalid";
    String TYPE_PATTERN = "pattern";

    String EMPTY_STRING = "";
}