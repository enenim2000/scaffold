package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.model.dao.Login;
import com.enenim.scaffold.util.ObjectMapperUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest extends RequestBody<Login> {
    private String username;
    private String password;

    public Login validateRequest() {
        return ObjectMapperUtil.map(this, Login.class);
    }

    public Login validateRequest(Login login) {
        return ObjectMapperUtil.map(this, login);
    }
}
