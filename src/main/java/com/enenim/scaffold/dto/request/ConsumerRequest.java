package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.constant.ValidationConstant;
import com.enenim.scaffold.model.dao.Consumer;
import com.enenim.scaffold.shared.ErrorValidator;
import com.enenim.scaffold.shared.Validation;
import com.enenim.scaffold.util.ObjectMapperUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Transient;

@Getter
@Setter
@ToString
public class ConsumerRequest extends RequestBody<Consumer>{

    @Transient
    public static final String MODEL_KEY = "consumer";

    @Transient
    public static final String MODEL_KEY_INVALID = MODEL_KEY + ".id.invalid";

    @Transient
    public static final String FIRST_NAME = "first_name";

    @Transient
    public static final String LAST_NAME = "last_name";

    @Transient
    public static final String EMAIL = "email";

    @Transient
    public static final String PHONE_NUMBER = "phone_number";

    @JsonProperty(FIRST_NAME)
    private String firstName;

    @JsonProperty(LAST_NAME)
    private String lastName;

    private String email;

    @JsonProperty(PHONE_NUMBER)
    private String phoneNumber;

    public ConsumerRequest(){
        super();
        validateRequest();
    }

    @Override
    public Consumer buildModel() {
        return ObjectMapperUtil.map(this, Consumer.class);
    }

    @Override
    public Consumer buildModel(Consumer consumer) {
        return ObjectMapperUtil.map(this, consumer);
    }

    @Override
    public Validation validateRequest(){
        ErrorValidator errorValidator = new ErrorValidator();
        new ErrorValidator()
                .addError(Validation.validateInput(getFirstName(), MODEL_KEY, FIRST_NAME,  ValidationConstant.TYPE_REQUIRED))
                .addError(Validation.validateInput(getLastName(), MODEL_KEY, LAST_NAME,  ValidationConstant.TYPE_REQUIRED))
                .addError(Validation.validateInput(getEmail(), MODEL_KEY, EMAIL, ValidationConstant.TYPE_REQUIRED))
                .addError(Validation.validateInput(getEmail(), MODEL_KEY, EMAIL, ValidationConstant.TYPE_PATTERN))
                .addError(Validation.validateInput(getPhoneNumber(), MODEL_KEY, PHONE_NUMBER, ValidationConstant.TYPE_REQUIRED));
        return new Validation(errorValidator.build());
    }
}