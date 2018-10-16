package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.model.dao.Login;
import com.enenim.scaffold.util.ObjectMapperUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class LoginRequest extends RequestBody<Login>{

    @NotNull
    private String username;

    private String password;

    @Override
    Login validateRequest() {
        return ObjectMapperUtil.map(this, Login.class);
    }

    @Override
    public Login validateRequest(Login login) {
        return ObjectMapperUtil.map(this, login);
    }
}
