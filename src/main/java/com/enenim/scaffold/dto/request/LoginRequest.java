package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.model.dao.Login;
import com.enenim.scaffold.util.ObjectMapperUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class LoginRequest extends RequestBody<Login>{

    @NotBlank(message = "@{login.username.required}")
    private String username;

    @NotBlank(message = "@{login.password.required}")
    private String password;

    @Override
    public Login buildModel() {
        return ObjectMapperUtil.map(this, Login.class);
    }

    @Override
    public Login buildModel(Login login) {
        return ObjectMapperUtil.map(this, login);
    }
}
