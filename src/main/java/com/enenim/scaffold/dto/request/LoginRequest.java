package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.constant.ValidationConstant;
import com.enenim.scaffold.model.dao.Login;
import com.enenim.scaffold.shared.ErrorValidator;
import com.enenim.scaffold.shared.Validation;
import com.enenim.scaffold.util.ObjectMapperUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Transient;

@Getter
@Setter
@ToString
public class LoginRequest extends RequestBody<Login>{

    @Transient
    public static final String MODEL_KEY = "login";

    @Transient
    public static final String MODEL_KEY_INVALID = MODEL_KEY + ".id.invalid";

    @Transient
    public static final String USERNAME = "username";

    @Transient
    public static final String PASSWORD = "password";

    private String username;

    private String password;

    public LoginRequest(){
        super();
        validateRequest();
    }

    @Override
    public Login buildModel() {
        return ObjectMapperUtil.map(this, Login.class);
    }

    @Override
    public Login buildModel(Login login) {
        return ObjectMapperUtil.map(this, login);
    }

    @Override
    public Validation validateRequest() {
        ErrorValidator errorValidator = new ErrorValidator();
        new ErrorValidator()
                .addError(Validation.validateInput(getUsername(), MODEL_KEY, USERNAME,  ValidationConstant.TYPE_REQUIRED))
                .addError(Validation.validateInput(getPassword(), MODEL_KEY, PASSWORD,  ValidationConstant.TYPE_REQUIRED));
        return new Validation(errorValidator.build());
    }
}
